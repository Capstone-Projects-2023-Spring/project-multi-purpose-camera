import os
from mimetypes import MimeTypes

import boto3
import settings
from Database.Data.Recording import Recording
from Database.MPCDatabase import MPCDatabase
from settings import AWS_SERVER_SECRET_KEY, AWS_SERVER_PUBLIC_KEY, PREFIX, MEDIACONVERT_ENDPOINT, \
    JOB_TEMPLATE_NAME, MEDIACONVERT_ROLE, CONVERTED


class VideoRetriever:

    def __init__(self, bucket: str):
        session = boto3.Session(
            aws_access_key_id=AWS_SERVER_PUBLIC_KEY,
            aws_secret_access_key=AWS_SERVER_SECRET_KEY
        )

        self.s3 = session.client('s3')
        self.bucket = bucket

    def post(self, response: dict, data):
        import requests
        files = {'file': data}
        r = requests.post(
            response["url"],
            data={
                "key": response["key"],
                "AWSAccessKeyId": response["AWSAccessKeyId"],
                "policy": response["policy"],
                "signature": response["signature"]
            },
            files=files)
        print(r.status_code)

    def pre_signed_url_get(self, key: str, expire: int):
        mime = MimeTypes()
        content_type = mime.guess_type(key)[0]
        if key[-len(".ts"):] == ".ts":
            content_type = "video/MP2T"
        return self.s3.generate_presigned_url(
            ClientMethod='get_object',
            Params={
                'Bucket': self.bucket,
                'Key': key,
                'ResponseContentType': content_type
            },
            ExpiresIn=expire
        )

    def get_all(self, channelArn: str):

        response = self.s3.list_objects(
            Bucket=self.bucket,
            Prefix=f"{PREFIX}{channelArn.split(':')[4]}/{channelArn.split('/')[1]}"
        )
        file_names = []

        for video in response['Contents']:
            splitted_key = video["Key"].split("/")
            if len(splitted_key) > 9 and splitted_key[11] == "hls" and splitted_key[12] == "720p30":
                file_names.append(video["Key"])

        return file_names

    def convert_stream_in_account(self, database: MPCDatabase, account_id, id_to_folder_stream_list_map: [str, dict[str, list[str]]]):
        for id in id_to_folder_stream_list_map:
            for folder in id_to_folder_stream_list_map[id]:
                self.convert_stream_to_one_vid(database, account_id, id, folder, id_to_folder_stream_list_map[id][folder])

    def convert_stream_to_one_vid(self, database: MPCDatabase, account_id, hardware_id, title: str, keys: list[str]):
        settings = self.make_settings(title, keys)
        user_metadata = {
            'JobCreatedBy': 'videoConvertSample',
        }

        database.insert(Recording(title, account_id=account_id, hardware_id=hardware_id))
        print(f"Video registered: account_id: {account_id} hardware_id: {hardware_id} title: {title}")
        client = boto3.client('mediaconvert', endpoint_url=MEDIACONVERT_ENDPOINT)
        result = client.create_job(
            Role=MEDIACONVERT_ROLE,
            JobTemplate=JOB_TEMPLATE_NAME,
            # 入力ファイルの情報や、上書きしたいパラメータの情報などを渡す
            Settings=settings,
            # タスクにユーザ定義のデータを紐付けることもできる。
            # キーと値が両方 `str` でないとダメ。
            UserMetadata=user_metadata,
        )

    def unregistered_stream_map_from_channels(self, recordings: list[Recording], channel_id_map: dict[str, str], resolution_p: str = "720p", fps: str = "30"):
        id_to_folder_stream_list_map = {}

        for arn in channel_id_map:
            id = channel_id_map[arn]
            id_to_folder_stream_list_map[id] = self.unregistered_stream_map_from_channel(recordings, arn, resolution_p, fps)

        return id_to_folder_stream_list_map

    def unregistered_stream_map_from_channel(self, recordings: list[Recording], channelArn: str, resolution_p: str = "720p", fps: str = "30"):
        folder_to_stream_list_map = {}

        account_id =channelArn.split(':')[4]
        stream_id = channelArn.split('/')[1]
        stream_file_prefix = f"{PREFIX}/{account_id}/{stream_id}/"
        stream_response = self.s3.list_objects(
            Bucket=self.bucket,
            Prefix=stream_file_prefix
        )

        if "Contents" in stream_response:
            stream_files = [i["Key"] for i in stream_response["Contents"]]
        else:
            stream_files = []

        registered_file_names = [r.file_name for r in recordings]

        for file in stream_files:
            basename = os.path.basename(file)
            folder_name = file.replace(basename, "")
            if file[-len(".ts"):] != ".ts" or folder_name[-len(f"{resolution_p}{fps}/"):] != f"{resolution_p}{fps}/":
                continue
            folder_name = f"{stream_id}/" + folder_name\
                .replace(f"/media/hls/{resolution_p}{fps}/", "")\
                .replace(stream_file_prefix, "")\
                .replace("/", "-")
            if folder_name in registered_file_names:
                continue
            if folder_name not in folder_to_stream_list_map:
                folder_to_stream_list_map[folder_name] = [file]
            else:
                folder_to_stream_list_map[folder_name].append(file)
        return folder_to_stream_list_map

    def converted_streams(self, channelArnList: list[str]):
        converted_files = []
        for channelArn in set(channelArnList):
            stream_id = channelArn.split('/')[1]
            converted_file_prefix = f"{CONVERTED}/{stream_id}"
            converted_response = self.s3.list_objects(
                Bucket=self.bucket,
                Prefix=converted_file_prefix
            )

            if "Contents" in converted_response:
                for item in converted_response["Contents"]:
                    split_item = item["Key"].split("/")
                    if len(split_item) == 4:
                        converted_files.append(f"{split_item[1]}/{split_item[2]}")
        return converted_files

    def get_thumbnail_key(self, folder: str):
        return f"{PREFIX}/{settings.ACCOUNT_ID}/{folder.replace('-', '/')}/media/thumbnails/thumb0.jpg"

    def make_input(self, key):
        return {
            "AudioSelectors": {
                "Audio Selector 1": {
                    "Offset": 0,
                    "DefaultSelection": "DEFAULT",
                    "SelectorType": "LANGUAGE_CODE",
                    "ProgramSelection": 1,
                    "LanguageCode": "ENM"
                }
            },
            "FileInput": f"s3://{self.bucket}/{key}"
        }

    def make_settings(self, title: str, keys: list[str]):

        # APIリファレンスを参考に設定
        return \
            {
                "Inputs": [self.make_input(k) for k in keys],
                "OutputGroups": [
                    {
                        "OutputGroupSettings": {
                            "FileGroupSettings": {
                                # 出力先パス。別バケットも可。トリガーの設定に応じて適宜変更してください。
                                "Destination": f"s3://{self.bucket}/converted/{title}/"
                            }
                        }
                    }
                ],
            }
