import base64
from mimetypes import MimeTypes
import boto3
import settings

session = boto3.Session(
        aws_access_key_id=settings.AWS_SERVER_PUBLIC_KEY,
        aws_secret_access_key=settings.AWS_SERVER_SECRET_KEY
    )

s3 = session.client('s3')


def pre_signed_url_post(bucket: str, key: str, expire: int = 3600):
    response = s3.generate_presigned_post(
        Bucket=bucket,
        Key=key,
        ExpiresIn=expire,
    )

    return {
        "url": response['url'],
        "key": response['fields']["key"],
        "AWSAccessKeyId": response['fields']["AWSAccessKeyId"],
        "policy": response['fields']["policy"],
        "signature": response['fields']["signature"]
    }


def post(response: dict, data):
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


def pre_signed_url_get(bucket: str, key: str, expire: int):
    mime = MimeTypes()
    content_type = mime.guess_type(key)[0]

    return s3.generate_presigned_url(
        ClientMethod='get_object',
        Params={
            'Bucket': bucket,
            'Key': key,
            'ResponseContentType': content_type
        },
        ExpiresIn=expire
    )


if __name__ == "__main__":

    # data = open("assets/bird-thumbnail.jpg", "rb").read()
    # print(data)
    response = {
    "url": "https://mpc-capstone.s3.amazonaws.com/",
    "key": "test_url.txt",
    "AWSAccessKeyId": "AKIAQYOVQX7RESALYLKX",
    "policy": "eyJleHBpcmF0aW9uIjogIjIwMjMtMDQtMTVUMDQ6MDQ6MzdaIiwgImNvbmRpdGlvbnMiOiBbeyJidWNrZXQiOiAibXBjLWNhcHN0b25lIn0sIHsia2V5IjogInRlc3RfdXJsLnR4dCJ9XX0=",
    "signature": "T42uGlosogs7npnOX+lTRjatf2M="
}
    data = "Secret message".encode("ascii")
    print("Secret message".encode("ascii"))
    post(response, data)
    # url = pre_signed_url_get(bucket, "bird_extra.jpg", 3600)
    # print(url)
