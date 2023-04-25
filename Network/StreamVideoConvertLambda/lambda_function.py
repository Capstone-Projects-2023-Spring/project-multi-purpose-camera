import json
import os
import boto3
from datetime import datetime

BUCKET = os.environ.get('BUCKET')
MEDIACONVERT_ENDPOINT = os.environ.get('MEDIACONVERT_ENDPOINT')
JOB_TEMPLATE_NAME = os.environ.get('JOB_TEMPLATE_NAME')
MEDIACONVERT_ROLE = os.environ.get('MEDIACONVERT_ROLE')
PREFIX = os.environ.get('PREFIX')
ACCOUNT_ID = os.environ.get('ACCOUNT_ID')


def lambda_handler(event, context):
    # TODO implement
    s3 = boto3.client("s3")

    channelArn = event["arn"].split("/")[1]

    print("MediaConvert: Request received")

    response = s3.list_objects(
        Bucket="mpc-capstone",
        Prefix=f"{PREFIX}/{ACCOUNT_ID}/{channelArn}"
    )

    print(response)

    stream_key_files = {}

    index = len(response["Contents"]) - 1
    index_date = None
    while index > 0:
        if response["Contents"][index]["Key"][-len(".ts"):] != ".ts":
            index = index - 1
            continue
        split_file = response["Contents"][index]["Key"].split("/")

        if index_date is None or datetime(int(split_file[4]), int(split_file[5]), int(split_file[6]),
                                          int(split_file[7]), int(split_file[8])) == index_date:

            index_date = datetime(int(split_file[4]), int(split_file[5]), int(split_file[6]), int(split_file[7]),
                                  int(split_file[8]))
            folder = f"{channelArn}/{index_date.strftime('%Y-%m-%d %H:%M:%S')}-{split_file[9]}"
            stream_key_files[folder] = []
            basename = os.path.basename(response["Contents"][index]["Key"])
            basename = basename.replace(".ts", "")
            number = int(basename)
            for j in range(number + 1):
                stream_key_files[folder].append(response["Contents"][index - j]["Key"])
            index = index - (number + 1 + 2) * 4 - 3 - 3
            stream_key_files[folder].reverse()
        else:
            break

    print(stream_key_files)
    for key in stream_key_files:
        print("MediaConvert: Converting " + key)
        settings = make_settings(key, stream_key_files[key])
        user_metadata = {
            'JobCreatedBy': 'videoConvertSample',
        }

        client = boto3.client('mediaconvert', endpoint_url=MEDIACONVERT_ENDPOINT)
        result = client.create_job(
            Role=MEDIACONVERT_ROLE,
            JobTemplate=JOB_TEMPLATE_NAME,
            Settings=settings,
            UserMetadata=user_metadata,
        )
    return {
        'statusCode': 200,
        'body': json.dumps('Hello from Lambda!')
    }


def make_input(key):
    return {
        "AudioSelectors": {
            "Audio Selector 1": {
                "Offset": 0,
                "DefaultSelection": "DEFAULT",
                "SelectorType": "LANGUAGE_CODE",
                "ProgramSelection": 1,
                "LanguageCode": "ENM"
            }
        },
        "FileInput": f"s3://{BUCKET}/{key}"
    }


def make_settings(title: str, keys: list[str]):
    return \
        {
            "Inputs": [make_input(k) for k in keys],
            "OutputGroups": [
                {
                    "OutputGroupSettings": {
                        "FileGroupSettings": {
                            "Destination": f"s3://{BUCKET}/converted/{title}/"
                        }
                    }
                }
            ],
        }