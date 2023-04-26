import json

from EB_EventDispatcher import EB_EventDispatcher
from IVS_Channel import IVS_Channel
from requests import Response


def json_payload(body, error=False):
    """If there's an error, return an error, if not, then return the proper status code, headers, and body"""
    print(body)
    if error:
        return {
            'statusCode': 400,
            'headers': {'Content-Type': 'application/json'},
            'body': json.dumps(body)
        }
    return {
        'statusCode': 200,
        'headers': {'Content-Type': 'application/json'},
        'body': json.dumps(body)
    }


def forward_response(response: Response):
    print("forwarding")
    if response.status_code == 200:
        return json_payload(response.json())
    return json_payload(response.json(), True)


def lambda_handler(event, context):

    body = json.loads(event["body"])

    _token = body["token"]
    # check if token is valid

    ivs = IVS_Channel(_token)

    r = ivs.verify_token()

    if r.status_code == 400:
        return json_payload(r.json()["body"], True)

    # if there is a device_id in the json
    if "device_id" not in body:
        if "device_name" in body:
            data = ivs.create_channel(body["device_name"])
        else:
            data = ivs.create_channel()
        _device_id = data["device_id"]
        _arn = data["arn"]
        ivs.register_device(data)
        ivs.link_device(_device_id)
        event_dispatcher = EB_EventDispatcher(_arn)
        rule_arn = event_dispatcher.put_rule_target()
        event_dispatcher.setup_lambda(rule_arn)

    else:
        _device_id = body["device_id"]

    r = ivs.get_device(_device_id)

    return forward_response(r)


if __name__ == "__main__":
    event = {
        "resource": "/account/verify/token",
        "httpMethod": "POST",
        "body": """{
                "username": "John Smith",
                "password": "Password",
                "email": "default@temple.edu",
                "code": "658186",
                "token": "3d17b1f7ace4bad5aaca513bf386bbd6",
                "device_id": "asdsadasd"
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