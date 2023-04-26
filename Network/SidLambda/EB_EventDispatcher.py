import json
import boto3
import os

LAMBDA = os.environ.get("LAMBDA")
FUNCTION_NAME = os.environ.get("FUNCTION_NAME")


class EB_EventDispatcher:
    def __init__(self, channel_arn: str, title: str = None):
        print("event dispatcher")
        self.channel_arn = channel_arn
        if title is not None:
            self.title = title
        else:
            self.title = self.channel_arn.split("/")[1]
        self.events = boto3.client('events')
        self._lambda = boto3.client("lambda")

        self.event_pattern = {
            "source": ["aws.ivs"],
            "resources": [self.channel_arn],
            "detail": {
                "recording_status": ["Recording End"]
            }
        }

    def put_rule_target(self) -> str:
        print("put rule and target")

        rule_arn = self.events.put_rule(
            Name=self.title,
            State='ENABLED',
            Description='Create from Lambda',
            EventPattern=json.dumps(self.event_pattern)
        )["RuleArn"]
        self.events.put_targets(
            Rule=self.title,
            Targets=[
                {
                    'Id': 'StartInstance',
                    'Arn': LAMBDA,
                    "Input": '{"arn": ' + '"' + f'{self.channel_arn}' + '"}'
                }
            ]
        )
        return rule_arn

    def setup_lambda(self, rule_arn: str):
        print("put set lambda permission")

        self._lambda.add_permission(
            FunctionName=FUNCTION_NAME,
            StatementId=self.channel_arn.split("/")[1],
            Action='lambda:InvokeFunction',
            Principal='events.amazonaws.com',
            SourceArn=rule_arn
        )
