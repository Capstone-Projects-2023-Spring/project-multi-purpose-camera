import json
import boto3
import os

LAMBDA = os.environ.get("LAMBDA")
FUNCTION_NAME = os.environ.get("FUNCTION_NAME")


def lambda_handler(event, context):
    channelArn = event["arn"]
    title = event["title"]

    eventclient = boto3.client('events')

    event_pattern = {
        "source": ["aws.ivs"],
        "resources": [channelArn],
        "detail": {
            "recording_status": ["Recording End"]
        }
    }

    rule_response = eventclient.put_rule(
        Name=title,
        State='ENABLED',
        Description='Create from Lambda',
        EventPattern=json.dumps(event_pattern)
    )

    target_response = eventclient.put_targets(
        Rule=title,
        Targets=[
            {
                'Id': 'StartInstance',
                'Arn': LAMBDA,
                "Input": '{"arn": ' + '"' + f'{channelArn}' + '"}'
            }
        ]
    )

    ret_lambda = boto3.client("lambda").add_permission(
        FunctionName=FUNCTION_NAME,
        StatementId=channelArn,
        Action='lambda:InvokeFunction',
        Principal='events.amazonaws.com',
        SourceArn=rule_response["RuleArn"]
    )