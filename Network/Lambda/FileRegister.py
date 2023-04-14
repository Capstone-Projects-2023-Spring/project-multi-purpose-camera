import boto3
import settings
from botocore.config import Config

session = boto3.Session(
    aws_access_key_id=settings.AWS_SERVER_PUBLIC_KEY,
    aws_secret_access_key=settings.AWS_SERVER_SECRET_KEY
)

s3 = session.client('s3', config=Config(signature_version='s3v4'))


# Generate the presigned URL for put requests
presigned_url = s3.generate_presigned_url(
    ClientMethod='put_object',
    Params={
        'Bucket': 'mpc-capstone',
        'Key': "MPCDatabaseAPI.docx"
    },
    ExpiresIn=3600,
    HttpMethod='PUT'
)

print(presigned_url)