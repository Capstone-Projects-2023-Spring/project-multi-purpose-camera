from mimetypes import MimeTypes

import boto3
import settings


class VideoRetriever:

    def __init__(self, bucket: str):
        session = boto3.Session(
                aws_access_key_id=settings.AWS_SERVER_PUBLIC_KEY,
                aws_secret_access_key=settings.AWS_SERVER_SECRET_KEY
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
            Prefix=f"{settings.PREFIX}{channelArn.split(':')[4]}/{channelArn.split('/')[1]}"
        )
        file_names = []

        for video in response['Contents']:
            splitted_key = video["Key"].split("/")
            if len(splitted_key) > 9 and splitted_key[11] == "hls" and splitted_key[12] == "720p30":
                file_names.append(video["Key"])

        return file_names


if __name__ == "__main__":

    # data = open("assets/bird-thumbnail.jpg", "rb").read()
    # print(data)
#     response = {
#     "url": "https://mpc-capstone.s3.amazonaws.com/",
#     "key": "sample.txt",
#     "AWSAccessKeyId": "AKIAQYOVQX7RESALYLKX",
#     "policy": "eyJleHBpcmF0aW9uIjogIjIwMjMtMDQtMTVUMDU6NDY6MDdaIiwgImNvbmRpdGlvbnMiOiBbeyJidWNrZXQiOiAibXBjLWNhcHN0b25lIn0sIHsia2V5IjogInNhbXBsZS50eHQifV19",
#     "signature": "vJvxhnm49bBcaOXD0Q6lYHkL4P4="
# }
#     data = "Another Secret message".encode("ascii")
#     print("Secret message".encode("ascii"))
#     post(response, data)
    video_retriever =  VideoRetriever(settings.BUCKET)
    print(video_retriever.get_all("arn:aws:ivs:us-east-1:052524269538:channel/HCBh4loJzOvw"))
#     print(video_retriever.pre_signed_url_get("bird.png", expire=3600))

    # url = pre_signed_url_get(bucket, "bird_extra.jpg", 3600)
    # print(url)


