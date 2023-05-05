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
    s3 = boto3.client("s3")

    channelArn = event["arn"].split("/")[1]

    print("MediaConvert: Request received")

    response = s3.list_objects(
        Bucket="mpc-capstone",
        Prefix=f"{PREFIX}/{ACCOUNT_ID}/{channelArn}"
    )

    max_date = response["Contents"][0]['LastModified']
    max_file = ""
    for file in response["Contents"]:
        if (max_date - file["LastModified"]).days < 0:
            max_date = file['LastModified']
            max_file = file["Key"]

    print(max_file)

    split_file = max_file.split("/")
    prefix = "/".join(split_file[:10])
    print(prefix)

    prefix += "/media/hls/"

    resolution_key_map = {}
    for file in response["Contents"]:
        if file["Key"][-len(".ts"):] == ".ts" and file["Key"][:len(prefix)] == prefix:
            split_file = file["Key"].split("/")
            if split_file[12] not in resolution_key_map:
                resolution_key_map[split_file[12]] = [file["Key"]]
            else:
                resolution_key_map[split_file[12]].append(file["Key"])
    print(resolution_key_map)
    resolution_key_map_sort = {}
    for key in resolution_key_map:
        resolution_key_map_sort[int(key.split("p")[0])] = resolution_key_map[key]

    print(resolution_key_map_sort)

    max_key = max(resolution_key_map_sort.keys())
    print(max_key)
    print(resolution_key_map_sort[max_key])

    if len(resolution_key_map_sort[max_key]) > 0:
        resolution_key_map_sort[max_key].pop()

    file_number_map = {}
    for file in resolution_key_map_sort[max_key]:
        splitted = file.split("/")
        file_number_map[int(splitted[-1].split(".")[0])] = file

    keys_sorted = list(file_number_map.keys())

    keys_sorted.sort()

    stream_files = []

    for key in keys_sorted:
        stream_files.append(file_number_map[key])

    split_prefix = prefix.split("/")
    key = f"{split_prefix[3]}/{'-'.join(split_prefix[4:10])}"

    print("MediaConvert: Converting " + key)
    settings = make_settings(key, stream_files)
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