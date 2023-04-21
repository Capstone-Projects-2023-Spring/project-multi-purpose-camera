from mimetypes import MimeTypes

from settings  import BUCKET, AWS_SERVER_SECRET_KEY, AWS_SERVER_PUBLIC_KEY, PREFIX


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


# if __name__ == "__main__":
#
#     # data = open("assets/bird-thumbnail.jpg", "rb").read()
#     # print(data)
# #     response = {
# #     "url": "https://mpc-capstone.s3.amazonaws.com/",
# #     "key": "sample.txt",
# #     "AWSAccessKeyId": "AKIAQYOVQX7RESALYLKX",
# #     "policy": "eyJleHBpcmF0aW9uIjogIjIwMjMtMDQtMTVUMDU6NDY6MDdaIiwgImNvbmRpdGlvbnMiOiBbeyJidWNrZXQiOiAibXBjLWNhcHN0b25lIn0sIHsia2V5IjogInNhbXBsZS50eHQifV19",
# #     "signature": "vJvxhnm49bBcaOXD0Q6lYHkL4P4="
# # }
# #     data = "Another Secret message".encode("ascii")
# #     print("Secret message".encode("ascii"))
# #     post(response, data)
#     video_retriever =  VideoRetriever(settings.BUCKET)
#     print(video_retriever.get_all("arn:aws:ivs:us-east-1:052524269538:channel/HCBh4loJzOvw"))
# #     print(video_retriever.pre_signed_url_get("bird.png", expire=3600))
#
#     # url = pre_signed_url_get(bucket, "bird_extra.jpg", 3600)
#     # print(url)

import os
import logging
import urllib.parse
import boto3

logger = logging.getLogger(__name__)
logger.setLevel(logging.INFO)

# MediaConvertのエンドポイントURL
MEDIACONVERT_ENDPOINT = "https://q25wbt2lc.mediaconvert.us-east-1.amazonaws.com"

# 前回作成したジョブテンプレートの名前
JOB_TEMPLATE_NAME = "MPC_Stream_Recording_To_MP4"

bucket = "mpc-capstone"

# MEDIACONVERT_ROLE = "arn:aws:iam::052524269538:role/MediaConvertLambdaRole"
MEDIACONVERT_ROLE = "arn:aws:iam::052524269538:role/MediaConvertLambdaRole"


def convert_video(key):
    settings = make_settings(bucket, key)
    user_metadata = {
        'JobCreatedBy': 'videoConvertSample',
    }

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

    logger.info(str(result))


def make_settings(bucket, key):
    basename = os.path.basename(key).split('.')[0]

    # APIリファレンスを参考に設定
    return \
        {
            "Inputs": [
                {
                    "AudioSelectors": {
                        "Audio Selector 1": {
                            "Offset": 0,
                            "DefaultSelection": "DEFAULT",
                            "SelectorType": "LANGUAGE_CODE",
                            "ProgramSelection": 1,
                            "LanguageCode": "ENM"
                        }
                    },
                    "FileInput": f"s3://{bucket}/{key}"
                }
            ],
            "OutputGroups": [
                {
                    "OutputGroupSettings": {
                        "FileGroupSettings": {
                            # 出力先パス。別バケットも可。トリガーの設定に応じて適宜変更してください。
                            "Destination": f"s3://{bucket}/converted/{basename}/"

                        }
                    }
                }
            ],
        }
