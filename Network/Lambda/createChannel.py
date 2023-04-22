import json
import boto3

def lambda_handler(event, context):
    # TODO implement
    ivs_client = boto3.client('ivs')
    response = ivs_client.create_channel(
        authorized=True,
        latencyMode='NORMAL',
        name=str(event['device_id']),
        recordingConfigurationArn='arn:aws:ivs:us-east-1:052524269538:recording-configuration/U1sJPpe9PfZg',
        type='STANDARD'
        )
    return response
