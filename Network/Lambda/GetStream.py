import boto3
import requests
import os


def lambda_handler(event, context):
    _api_key = os.environ.get("KEY")
    headers = {
        "x-api-key": _api_key
    }
    body = event["body"]

    _token = body["token"]
    # register hardware to server
    json = {
        "token": _token
    }
    r = requests.put('https://nk0fs4t630.execute-api.us-east-1.amazonaws.com/product2/account/verify/token',
                     json=json, headers=headers)
    print(r)
    print(r.status_code)

    if "device_id" in event:
        _device_id = event["device_id"]

    ivs_client = boto3.client('ivs')
    # if there is a device_id in the json
    if not "device_id" in event:
        # create a device_id for the device
        device_id = random_by_hash()
        # create a channel for the device
        response = ivs_client.create_channel(
            authorized=True,
            insecureIngest=False,
            latencyMode='NORMAL',
            name=device_id,
            recordingConfigurationArn="",
            type='STANDARD'
        )
        # parse response for data
        _device_id = ""
        _max_resolution = "720p"
        _channel_name = ""
        _playback_url = ""
        _ingest_endpoint = ""
        _stream_key = ""
        _device_name = "MP-Camera"
        _arn = ""
        _s3_recording_prefix = ""
        if "channel" in response:
            if "name" in response["channel"]:
                _device_id = response["channel"]["name"]
                _channel_name = response["channel"]["name"]
            if "ingestEndpoint" in response["channel"]:
                _ingest_endpoint = response["channel"]["ingestEndpoint"]
            if "playbackUrl" in response["channel"]:
                _playback_url = response["channel"]["playbackUrl"]
        if "streamKey" in response:
            if "value" in response["streamKey"]:
                _stream_key = response["streamKey"]["value"]
            if "arn" in response["streamKey"]:
                _arn = response["streamKey"]["arn"]

        # register hardware to server
        json = {
            "device_id": _device_id,
            "max_resolution": _max_resolution,
            "channel_name": _channel_name,
            "playback_url": _playback_url,
            "ingest_endpoint": _ingest_endpoint,
            "stream_key": _stream_key,
            "device_name": _device_name,
            "arn": _arn
        }
        r = requests.put('https://nk0fs4t630.execute-api.us-east-1.amazonaws.com/product2/hardware/register', json=json,
                         headers=headers)
        # link device to account
        json = {
            "device_id": _device_id,
            "token": _token
        }
        r = requests.put('https://nk0fs4t630.execute-api.us-east-1.amazonaws.com/product2/account/add/device',
                         json=json, headers=headers)


    return r.json()


def random_by_hash():
    import hashlib
    import datetime
    c = datetime.datetime.now().strftime('%Y/%m/%d %H:%M:%S.%f')[:-3]
    dat = 'python' + c

    hs = hashlib.sha224(dat.encode()).hexdigest()
    return hs


if __name__ == "__main__":
    event = {
        "resource": "/account/verify/token",
        "httpMethod": "POST",
        "body": """{
                "username": "John Smith",
                "password": "Password",
                "email": "default@temple.edu",
                "code": "658186",
                "token": "value1",
                "device_id": "60df7562bc4e566abe803c448f5609ea"
            }""",
        "pathParameters": {
            "key": "sample.txt"
        },
        "queryStringParameters": {
            "notification_type": 10
        }
    }
    response = lambda_handler(event, None)