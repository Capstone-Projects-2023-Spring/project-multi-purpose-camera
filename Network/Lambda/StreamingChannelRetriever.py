import boto3
import settings

session = boto3.Session(
    aws_access_key_id=settings.AWS_SERVER_PUBLIC_KEY,
    aws_secret_access_key=settings.AWS_SERVER_SECRET_KEY
)

s3 = session.client('ivs', region_name='us-east-1')

def start_recording(arn: str):
    response = s3.update_channel(
        arn="arn:aws:ivs:us-east-1:052524269538:channel/HCBh4loJzOvw",
        recordingConfigurationArn=settings.IVS_RECORDING_ARN
    )
    return response

if __name__ == "__main__":
    print(response["channel"])
# response = s3.create_recording_configuration(
#     destinationConfiguration={
#         's3': {
#             'bucketName': settings.BUCKET
#         }
#     },
#     name='new_string_config'
# )
# print(response["recordingConfiguration"])
