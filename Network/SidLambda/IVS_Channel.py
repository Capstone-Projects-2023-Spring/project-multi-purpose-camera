import os

import boto3
import requests


class IVS_Channel:
    def __init__(self, token: str):
        self.token = token
        _api_key = os.environ.get("KEY")
        self.headers = {
            "x-api-key": _api_key
        }

    def verify_token(self):
        json = {
            "token": self.token
        }
        return requests.post('https://nk0fs4t630.execute-api.us-east-1.amazonaws.com/product2/account/verify/token',
                          json=json, headers=self.headers)

    def create_channel(self):
        ivs_client = boto3.client('ivs')
        # create a device_id for the device
        device_id = self.random_by_hash()
        # create a channel for the device
        response = ivs_client.create_channel(
            authorized=True,
            insecureIngest=False,
            latencyMode='NORMAL',
            name=device_id,
            recordingConfigurationArn="",
            type='STANDARD'
        )

        # register hardware to server
        json = {
            "device_id": response["channel"]["name"],
            "max_resolution": "720p",
            "channel_name": response["channel"]["name"],
            "playback_url": response["channel"]["playbackUrl"],
            "ingest_endpoint": response["channel"]["ingestEndpoint"],
            "stream_key": response["streamKey"]["value"],
            "device_name": "MP-Camera",
            "arn": response["channel"]["arn"]
        }

        return json

    def random_by_hash(self):
        import hashlib
        import datetime
        c = datetime.datetime.now().strftime('%Y/%m/%d %H:%M:%S.%f')[:-3]
        dat = 'python' + c

        hs = hashlib.sha224(dat.encode()).hexdigest()
        return hs

    def register_device(self, json: dict):
        return requests.put('https://nk0fs4t630.execute-api.us-east-1.amazonaws.com/product2/hardware/register', json=json,
                         headers=self.headers)

    def link_device(self, device_id: str):
        json = {
            "device_id": device_id,
            "token": self.token
        }
        return requests.put('https://nk0fs4t630.execute-api.us-east-1.amazonaws.com/product2/account/add/device',
                         json=json,
                         headers=self.headers)

    def get_device(self, device_id: str):
        json = {
            "device_id": device_id,
            "token": self.token
        }
        return requests.post('https://nk0fs4t630.execute-api.us-east-1.amazonaws.com/product2/account/get/device',
                          json=json, headers=self.headers)