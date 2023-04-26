from requests import Response

import boto3
import requests
import os
import json as JSON


def json_payload(body, error=False):
    """If there's an error, return an error, if not, then return the proper status code, headers, and body"""
    print(body)
    if error:
        return {
            'statusCode': 400,
            'headers': {'Content-Type': 'application/json'},
            'body': JSON.dumps(body)
        }
    return {
        'statusCode': 200,
        'headers': {'Content-Type': 'application/json'},
        'body': JSON.dumps(body)
    }


def forward_response(response: Response):
    print("forwarding")
    if response.status_code == 200:
        return json_payload(response.json())
    return json_payload(response.json(), True)


def lambda_handler(event, context):
    _api_key = os.environ.get("KEY")
    headers = {
        "x-api-key": _api_key
    }
    body = JSON.loads(event["body"])

    _token = body["token"]
    # check if token is valid
    json = {
        "token": _token
    }
    r = requests.post('https://nk0fs4t630.execute-api.us-east-1.amazonaws.com/product2/account/verify/token',
                     json=json, headers=headers)
    if r.status_code == 400:
        return json_payload(r.json()["body"], True)

    # if there is a device_id in the json
    if "device_id" not in body:
        ivs_client = boto3.client('ivs')
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
        r = requests.put('https://nk0fs4t630.execute-api.us-east-1.amazonaws.com/product2/account/add/device', json=json,
                         headers=headers)

    else:
        _device_id = body["device_id"]

    json = {
        "device_id": _device_id,
        "token": _token
    }
    r = requests.post('https://nk0fs4t630.execute-api.us-east-1.amazonaws.com/product2/account/get/device',
                     json=json, headers=headers)

    return forward_response(r)


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
                "token": "3d17b1f7ace4bad5aaca513bf386bbd6"
            }""",
        "pathParameters": {
            "key": "sample.txt"
        },
        "queryStringParameters": {
            "notification_type": 10
        }
    }
    response = lambda_handler(event, None)
    print(response)