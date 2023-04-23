import json
import os
import random
import re

import EmailSender
import settings
from Database.Data.Account import Account
from Database.Data.Account_has_Hardware import Account_has_Hardware
from Database.Data.Criteria import Criteria
from Database.Data.Hardware import Hardware
from Database.Data.Hardware_has_Notification import Hardware_has_Notification
from Database.Data.Hardware_has_Saving_Policy import Hardware_has_Saving_Policy
from Database.Data.Notification import Notification
from Database.Data.Recording import Recording
from Database.Data.Resolution import Resolution
from Database.Data.Saving_Policy import Saving_Policy
from Database.MPCDatabase import MPCDatabase, MatchItem, JoinItem
from Error import Error
from StreamingChannelRetriever import Recorder
from VideoRetriever import VideoRetriever
from mpc_api import MPC_API

try:
    from settings import AWS_SERVER_PUBLIC_KEY, AWS_SERVER_SECRET_KEY, BUCKET
except:

    BUCKET = os.environ['BUCKET']
    AWS_SERVER_SECRET_KEY = os.environ['AWS_SERVER_SECRET_KEY']
    AWS_SERVER_PUBLIC_KEY = os.environ['AWS_SERVER_PUBLIC_KEY']

api = MPC_API()
database = MPCDatabase()


def lambda_handler(event, context):
    """Manages the database queries and speaks to the imported libraries to make things possible"""
    print(event)
    print(context)

    status = 200
    resource = event["resource"].lower()
    httpMethod = event["httpMethod"].lower()

    queryPara = {}
    pathPara = {}

    if "queryStringParameters" in event and event["queryStringParameters"] is not None:
        queryPara = event["queryStringParameters"]

    if "pathParameters" in event and event["pathParameters"] is not None:
        pathPara = event["pathParameters"]

    if "body" in event and event["body"] is not None:
        event["body"] = json.loads(event["body"])

    try:
        if resource in api.handlers:
            return api.handlers[resource][httpMethod](event, pathPara, queryPara)
        else:
            return api.handlers["/"]["get"]({"event": event, "context": str(context)}, pathPara, queryPara)
    except Exception as err:
        status = 500
        data = {"error": str(err)}

    return {
        'statusCode': status,
        'headers': {'Content-Type': 'application/json'},
        'body': json.dumps(data)
    }


def isNumber(sNum: str):
    try:
        int(sNum)
        return True
    except:
        return False


def json_payload(body, error=False):
    """If there's an error, return an error, if not, then return the proper status code, headers, and body"""
    print(body)
    if error:
        return {
            'statusCode': 400,
            'headers': {'Content-Type': 'application/json'},
            'body': json.dumps({"body": body})
        }
    return {
        'statusCode': 200,
        'headers': {'Content-Type': 'application/json'},
        'body': json.dumps(body)
    }


def check_email(email):
    """Returns true if the email is in the proper format, returns false if it's not"""
    regex = re.compile(r'([A-Za-z0-9]+[.-_])*[A-Za-z0-9]+@[A-Za-z0-9-]+(\.[A-Z|a-z]{2,})+')
    if re.fullmatch(regex, email):
        return True
    else:
        return False


def check_password(password):
    """Makes sure the password is in the correct format and is at least 8 characters"""
    if re.fullmatch(r"^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$", password):
        return True
    else:
        return False


def random_by_hash():
    import hashlib
    import datetime
    c = datetime.datetime.now().strftime('%Y/%m/%d %H:%M:%S.%f')[:-3]
    dat = 'python' + c

    hs = hashlib.sha224(dat.encode()).hexdigest()
    return hs


def get_all(table_class):
    """Gets all specified items in the database"""
    items: list[table_class] = database.get_all(table_class)
    dict_list = table_class.list_object_to_dict_list(items)

    return json_payload(dict_list)


def get_by_id(table_class, pathPara):
    """Gets all information in the database of the specified id"""
    id = pathPara["id"]
    item: table_class = database.get_by_id(table_class, id)
    body = table_class.object_to_dict(item)

    return json_payload(body)


def get_by_name(table_class, pathPara):
    """Gets all information based on the specified name"""
    name = pathPara["name"]
    resolution = database.get_by_name(table_class, name)
    body = table_class.object_to_dict(resolution)

    return json_payload(body)


def delete_by_id(table_class, pathPara):
    """Deletes information based on the specified id"""
    database.delete_by_field(table_class, (table_class.ID, pathPara["id"]))

    return json_payload({})


def delete_by_name(table_class, pathPara):
    """Deletes information from the database based on the specified name"""
    database.delete_by_field(table_class, (table_class.NAME, pathPara["name"]))

    return json_payload({})


def delete_by_hardware_id(table_class, pathPara):
    """Deletes information from the database based on the specified hardware id"""
    database.delete_by_field(table_class, (table_class.HARDWARE_ID, pathPara["hardware_id"]))

    return json_payload({})


def update_by_id(table_class, pathPara, queryPara):
    """Updates the database rows in a table based on the specified id"""
    id = pathPara["id"]
    update_keys = set(table_class.COLUMNS).intersection(queryPara.keys())
    if table_class.ID in update_keys:
        update_keys.remove(table_class.ID)
    database.update_fields(table_class, (table_class.ID, id), [(key, queryPara[key]) for key in update_keys])

    return json_payload({})


@api.handle("/")
def home(event, pathPara, queryPara):
    """Handles query events using the json libraries and returns a labeled array"""
    return {
        'statusCode': 200,
        'headers': {'Content-Type': 'application/json'},
        'body': json.dumps(event)
    }


@api.handle("/", httpMethod=MPC_API.POST)
def home(event, pathPara, queryPara):
    """Handles Query events using the json libraries and returns a labeled array"""

    return {
        'statusCode': 200,
        'headers': {'Content-Type': 'application/json'},
        'body': json.dumps(event)
    }


@api.handle("/account")
def accounts_request(event, pathPara, queryPara):
    """Gets all rows and columns of the Account table"""
    return get_all(Account)


@api.handle("/account/signup", httpMethod=MPC_API.POST)
def account_signup(event, pathPara, queryPara):
    """Handles new accounts from users and makes sure user information is in the correct format"""
    body = event["body"]
    error = []
    if database.verify_name(Account, body[Account.NAME]):
        error.append(Error.NAME_DUPLICATE)
    if database.verify_field(Account, Account.EMAIL, body[Account.EMAIL]):
        error.append(Error.EMAIL_DUPLICATE)
    if not check_email(body["email"]):
        error.append(Error.INVALID_EMAIL_FORMAT)
    if not check_password(body["password"]):
        error.append(Error.PASSWORD_WEAK)

    if len(error) == 0:
        database.insert(Account(body["username"], body["password"], body["email"], timestamp="NOW()"))
        return json_payload({"message": "Account created"})
    return json_payload({"message": "\n".join(error)}, True)


@api.handle("/account/profile", httpMethod=MPC_API.POST)
def account_signup(event, pathPara, queryPara):
    """Handles get user information request from users and makes sure user information is in the correct format"""
    body = event["body"]
    if not database.verify_field(Account, Account.TOKEN, body[Account.TOKEN]):
        json_payload({"message": Error.UNKNOWN_ACCOUNT}, True)

    account: Account = database.get_by_field(Account, Account.TOKEN, body[Account.TOKEN])
    return json_payload({"message": "Account found", Account.NAME: account.username,
                         Account.EMAIL: account.email,
                         Account.STATUS: account.status})


@api.handle("/account/signin", httpMethod=MPC_API.POST)
def account_signin(event, pathPara, queryPara):
    """Handles users signing into their account by verifying their username and password in the database"""
    body: dict = event["body"]

    if database.verify_fields(
            Account, [(Account.NAME, body[Account.NAME]), (Account.PASSWORD, body[Account.PASSWORD])]):
        field = Account.NAME

    elif database.verify_fields(
            Account, [(Account.EMAIL, body[Account.NAME]), (Account.PASSWORD, body[Account.PASSWORD])]):
        field = Account.EMAIL
    else:
        return json_payload({"message": "login failed: " + Error.LOGIN_FAILED}, True)

    timestamp_check = database.varidate_timestamp(Account, field, body[Account.NAME])
    database.update_fields(Account, (field, body[Account.NAME]),
                           [(Account.TOKEN, "md5(ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000))"),
                            (Account.TIMESTAMP, "NOW()")])
    if not timestamp_check:
        return json_payload({"message": "login failed: " + Error.TIMESTAMP_ERROR}, True)

    account: Account = database.get_by_field(Account, field, body[Account.NAME])
    return json_payload({"message": "Signed in to Account",
                         Account.TOKEN: account.token, Account.NAME: account.username, Account.EMAIL: account.email})


@api.handle("/account/reset", httpMethod=MPC_API.POST)
def account_signin(event, pathPara, queryPara):
    """Handles users reset their account by verifying their username in the database"""
    body: dict = event["body"]

    if database.verify_fields(
            Account, [(Account.NAME, body[Account.NAME])]):
        field = Account.NAME

    elif database.verify_fields(
            Account, [(Account.EMAIL, body[Account.NAME])]):
        field = Account.EMAIL
    else:
        return json_payload({"message": "Code sent: "})
    code = str(random.randint(100000, 999999))
    database.update_fields(Account, (field, body[Account.NAME]), [(Account.CODE, code)])
    print(code)
    account: Account = database.get_by_field(Account, field, body[Account.NAME])
    if EmailSender.send(account.email, "[MPC Account] Password reset Code", f"Reset code:  {account.code}"):
        return json_payload({"message": "Code sent"})
    else:
        return json_payload({"message": "Failed to send code"})


@api.handle("/account/code", httpMethod=MPC_API.POST)
def account_signin(event, pathPara, queryPara):
    """Handles users reset their account by verifying their username in the database"""
    body: dict = event["body"]

    if database.verify_fields(Account, [(Account.NAME, body[Account.NAME])]):
        field = Account.NAME

    elif database.verify_fields(Account, [(Account.EMAIL, body[Account.NAME])]):
        field = Account.EMAIL
    else:
        return json_payload({"message": Error.INCORRECT_CODE}, True)

    timestamp_check = database.varidate_timestamp(Account, field, body[Account.NAME])
    database.update_fields(Account, (field, body[Account.NAME]), [(Account.TIMESTAMP, "NOW()")])
    if not timestamp_check:
        return json_payload({"message": "login failed: " + Error.TIMESTAMP_ERROR}, True)

    if database.verify_fields(Account, [(field, body[Account.NAME]), (Account.CODE, body[Account.CODE])]):
        database.update_fields(Account, (field, body[Account.NAME]),
                               [(Account.TOKEN, "md5(ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000))"),
                                (Account.TIMESTAMP, "NOW()")])

        token = database.get_field_by_field(Account, Account.TOKEN, field, body[Account.NAME])
        return json_payload({"message": "Code confirmed", Account.TOKEN: token})
    return json_payload({"message": Error.INCORRECT_CODE}, True)


@api.handle("/account/password", httpMethod=MPC_API.POST)
def account_signin(event, pathPara, queryPara):
    """Handles users reset their account by verifying their username in the database"""
    body: dict = event["body"]

    if database.verify_fields(
            Account, [(Account.NAME, body[Account.NAME]), (Account.CODE, body[Account.CODE]),
                      (Account.TOKEN, body[Account.TOKEN])]
    ):
        field = Account.NAME

    elif database.verify_fields(
            Account, [(Account.EMAIL, body[Account.NAME]), (Account.CODE, body[Account.CODE]),
                      (Account.TOKEN, body[Account.TOKEN])]
    ):
        field = Account.EMAIL
    else:
        return json_payload({"message": Error.UNKNOWN_ACCOUNT}, True)

    timestamp_check = database.varidate_timestamp(Account, field, body[Account.NAME])
    database.update_fields(Account, (field, body[Account.NAME]),
                           [(Account.TOKEN, "md5(ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000))"),
                            (Account.PASSWORD, body[Account.PASSWORD]),
                            (Account.CODE, None),
                            (Account.TIMESTAMP, "NOW()")])
    if not timestamp_check:
        return json_payload({"message": "login failed: " + Error.TIMESTAMP_ERROR}, True)

    token = database.get_field_by_field(Account, Account.TOKEN, field, body[Account.NAME])
    return json_payload({"message": "Password reset successful", Account.TOKEN: token})


@api.handle("/account/verify/token", httpMethod=MPC_API.POST)
def account_signin(event, pathPara, queryPara):
    """Handles users reset their account by verifying their username in the database"""
    body: dict = event["body"]

    if database.verify_field(Account, Account.TOKEN, body[Account.TOKEN]):
        return json_payload({"message": "Token Found"})

    return json_payload({"message": Error.TOKEN_NOT_FOUND}, True)


@api.handle("/account/verify/device", httpMethod=MPC_API.POST)
def account_signin(event, pathPara, queryPara):
    """Handles users reset their account by verifying their username in the database"""
    body: dict = event["body"]

    if database.verify_fields_by_joins(Account,
                                       [(Account_has_Hardware, Account_has_Hardware.EXPLICIT_ACCOUNT_ID,
                                         Account.EXPLICIT_ID),
                                        (Hardware, Hardware.EXPLICIT_HARDWARE_ID,
                                         Account_has_Hardware.EXPLICIT_HARDWARE_ID)],
                                       [(Account.TOKEN, body[Account.TOKEN]),
                                        (Hardware.EXPLICIT_DEVICE_ID, body[Hardware.DEVICE_ID])]):
        return json_payload({"message": "Device Found"})
    return json_payload({"message": Error.DEVICE_NOT_FOUND}, True)


@api.handle("/account/add/device", httpMethod=MPC_API.PUT)
def account_signin(event, pathPara, queryPara):
    """Handles users reset their account by verifying their username in the database"""
    body: dict = event["body"]
    id_a = database.get_field_by_field(Account, Account.ID, Account.TOKEN, body[Account.TOKEN])
    id_h = database.get_field_by_field(Hardware, Hardware.ID, Hardware.DEVICE_ID, body[Hardware.DEVICE_ID])
    if id_a is None:
        return json_payload({"message": Error.TOKEN_MISMATCH}, True)
    if id_h is None:
        return json_payload({"message": Error.DEVICE_NOT_FOUND}, True)

    database.insert(Account_has_Hardware(id_a, id_h))
    # database.insert()
    return json_payload({"message": "Account associated with device"})


@api.handle("/account", httpMethod=MPC_API.POST)
def account_insert(event, pathPara, queryPara):
    """Inserts new row into the account table which represents a new user"""
    account: Account = Account(queryPara["username"], queryPara["password"], queryPara["email"], "C")
    database.insert(account)
    a: Account = database.get_by_name(Account, queryPara["username"])
    return json_payload({"id": a.account_id, "token": a.token})


@api.handle("/account/{id}")
def account_request_by_id(event, pathPara, queryPara):
    """Gets account based on specified id"""
    return get_by_id(Account, pathPara)


@api.handle("/hardware")
def hardware_request(event, pathPara, queryPara):
    """Gets all rows and columns of the hardware table"""
    return get_all(Hardware)


@api.handle("/hardware", httpMethod=MPC_API.POST)
def hardware_insert(event, pathPara, queryPara):
    """Inserts new rows into the hardware table based on account id"""
    if "account_id" in queryPara:
        hardware = Hardware(queryPara["name"], queryPara["max_resolution"], account_id=queryPara["account_id"])
    else:
        hardware = Hardware(queryPara["name"], queryPara["max_resolution"])
    database.insert(hardware)
    id = database.get_id_by_name(Hardware, queryPara["name"])
    return json_payload({"id": id})


@api.handle("/hardware/all", httpMethod=MPC_API.POST)
def hardware_insert(event, pathPara, queryPara):
    """Inserts new rows into the hardware table based on account id"""
    token = event["body"]["token"]

    hardware = database.get_all_join_fields_by_field(
        Hardware,
        [
            (Account_has_Hardware, Account_has_Hardware.EXPLICIT_HARDWARE_ID, Hardware.EXPLICIT_HARDWARE_ID),
            (Account, Account_has_Hardware.EXPLICIT_ACCOUNT_ID, Account.EXPLICIT_ID)
        ], Account.TOKEN, token)
    return json_payload({"hardware": Hardware.list_object_to_dict_list(hardware)})


@api.handle("/hardware/register", httpMethod=MPC_API.PUT)
def hardware_insert(event, pathPara, queryPara):
    """Inserts new rows into the hardware table based on account id"""
    body = event["body"]
    if Hardware.S3_RECORDING_PREFIX in body:
        s3_recording_prefix = body[Hardware.S3_RECORDING_PREFIX]
    else:
        s3_recording_prefix = None
    hardware = Hardware(body[Hardware.NAME], body[Hardware.RESOLUTION_NAME], body[Hardware.CHANNEL_NAME],
                        body[Hardware.ARN], body[Hardware.STREAM_KEY], body[Hardware.INGEST_ENDPOINT],
                        body[Hardware.PLAYBACK_URL], body[Hardware.DEVICE_ID], s3_recording_prefix)
    database.insert(hardware)
    inserted_hardware = database.get_by_field(Hardware, Hardware.DEVICE_ID, body[Hardware.DEVICE_ID])
    return json_payload({"hardware": Hardware.object_to_dict(inserted_hardware)})


@api.handle("/hardware/newname", httpMethod=MPC_API.POST)
def hardware_newname(event, pathPara, queryPara):
    """Inserts new rows into the hardware table based on account id"""
    body = event["body"]
    token = body[Account.TOKEN]
    if not database.verify_field(Account, Account.TOKEN, token):
        return json_payload({"message": Error.TOKEN_NOT_FOUND}, True)

    hardware: list[Hardware] = database.get_all_by_joins(Hardware,
                                                         [(Account_has_Hardware,
                                                           Account_has_Hardware.EXPLICIT_HARDWARE_ID,
                                                           Hardware.EXPLICIT_ID),
                                                          (Account, Account_has_Hardware.EXPLICIT_ACCOUNT_ID,
                                                           Account.EXPLICIT_ID)],
                                                         [(Account.TOKEN, token)])

    names = [h.device_name for h in hardware]

    prefix = "MPC Camera"
    if "prefix" in body:
        prefix = body["prefix"]

    prefixed_numbers = []
    for name in names:
        if len(name) > len(prefix) and name[:len(prefix)] == prefix:
            split_name = name.split(" ")
            if len(split_name) < 2:
                continue
            if isNumber(split_name[-1]):
                prefixed_numbers.append(int(split_name[-1]))

    if len(prefixed_numbers) <= 0:
        return json_payload({"name": f"{prefix} 0"})

    return json_payload({"name": f"{prefix} {max(prefixed_numbers) + 1}"})


@api.handle("/hardware/{id}")
def hardware_request_by_id(event, pathPara, queryPara):
    """Gets information from the hardware table based on specified id"""
    return get_by_id(Hardware, pathPara)


@api.handle("/hardware/{id}", httpMethod=MPC_API.DELETE)
def hardware_delete_by_id(event, pathPara, queryPara):
    """Deletes rows from the hardware table of the specified id"""
    return delete_by_id(Hardware, pathPara)


@api.handle("/hardware/{id}", httpMethod=MPC_API.PUT)
def hardware_update_by_id(event, pathPara, queryPara):
    """Updates the hardware table based on the specified id"""
    return update_by_id(Hardware, pathPara, queryPara)


@api.handle("/recording")
def recordings_request(event, pathPara, queryPara):
    """Gets recordings from the server and and formats the information from AWS into appropriate variables for processing"""
    recordings: list[Recording] = database.get_all(Recording)
    for rec in recordings:
        bucket = "mpc-capstone"
        rec.url = f"https://{bucket}.s3.amazonaws.com/{settings.CONVERTED}/{rec.file_name}"
        host = event["multiValueHeaders"]["Host"][0]
        stage = event["requestContext"]["stage"]
        path = "storage"
        rec.alt_url = f"https://{host}/{stage}/{path}/{bucket}/{rec.file_name}"

    dict_list = Recording.list_object_to_dict_list(recordings)

    return json_payload(dict_list)


@api.handle("/recording", httpMethod=MPC_API.POST)
def recording_insert(event, pathPara, queryPara):
    """Inserts a recording into the database of the specified account id"""
    recording = Recording(queryPara["file_name"], "CURDATE()", "NOW()",
                          account_id=queryPara["account_id"], hardware_id=queryPara["hardware_id"])
    recording.add_date_timestamp_from_query_para(queryPara)
    database.insert(recording)
    id = database.get_id_by_name(Recording, queryPara["file_name"])
    return json_payload({"id": id})


@api.handle("/recording/{id}")
def recording_request_by_id(event, pathPara, queryPara):
    """Gets recording from AWS and stores it in appropriate variables for processing"""
    id = pathPara["id"]
    recording = database.get_by_id(Recording, id)
    bucket = "mpc-capstone"
    recording.url = f"https://{bucket}.s3.amazonaws.com/{recording.file_name}"
    host = event["multiValueHeaders"]["Host"][0]
    stage = event["requestContext"]["stage"]
    path = "storage"
    recording.alt_url = f"https://{host}/{stage}/{path}/{bucket}/{recording.file_name}"
    body = Recording.object_to_dict(recording)

    return json_payload(body)


@api.handle("/recording/{id}", httpMethod=MPC_API.DELETE)
def recording_delete_by_id(event, pathPara, queryPara):
    """Deletes recording from table based on specified id"""
    return delete_by_id(Recording, pathPara)


@api.handle("/recording/{id}", httpMethod=MPC_API.PUT)
def recording_update_by_id(event, pathPara, queryPara):
    """Updates recording table based on specified id"""
    return update_by_id(Recording, pathPara, queryPara)


@api.handle("/recording/start", httpMethod=MPC_API.POST)
def recording_start(event, pathPara, queryPara):
    """Updates recording table based on specified id"""
    body = event["body"]
    if not database.verify_fields_by_joins(Account,
                                           [(Account_has_Hardware, Account_has_Hardware.EXPLICIT_ACCOUNT_ID,
                                             Account.EXPLICIT_ID),
                                            (Hardware, Hardware.EXPLICIT_HARDWARE_ID,
                                             Account_has_Hardware.EXPLICIT_HARDWARE_ID)],
                                           [(Account.TOKEN, body[Account.TOKEN]),
                                            (Hardware.EXPLICIT_DEVICE_ID, body[Hardware.DEVICE_ID])]):
        return json_payload({"message": "Device not Found"}, True)
    arn = database.get_field_by_field(Hardware, Hardware.ARN, Hardware.DEVICE_ID, body[Hardware.DEVICE_ID])
    recorder = Recorder(arn)
    try:
        recorder.request_single(Recorder.Type.STREAM_STOP)
    except Recorder.RecorderError:
        return json_payload({"message": "Could not stop stream"}, True)

    try:
        recorder.request_looper(Recorder.Type.START, 3, 5)
    except Recorder.RecorderError:
        return json_payload({"message": "Could not start recording"}, True)

    return json_payload({"message": "Recording started"})


@api.handle("/recording/stop", httpMethod=MPC_API.POST)
def recording_start(event, pathPara, queryPara):
    """Updates recording table based on specified id"""
    body = event["body"]
    if not database.verify_fields_by_joins(Account,
                                           [(Account_has_Hardware, Account_has_Hardware.EXPLICIT_ACCOUNT_ID,
                                             Account.EXPLICIT_ID),
                                            (Hardware, Hardware.EXPLICIT_HARDWARE_ID,
                                             Account_has_Hardware.EXPLICIT_HARDWARE_ID)],
                                           [(Account.TOKEN, body[Account.TOKEN]),
                                            (Hardware.EXPLICIT_DEVICE_ID, body[Hardware.DEVICE_ID])]):
        return json_payload({"message": "Device not Found"}, True)
    arn = database.get_field_by_field(Hardware, Hardware.ARN, Hardware.DEVICE_ID, body[Hardware.DEVICE_ID])
    recorder = Recorder(arn)
    try:
        recorder.request_single(Recorder.Type.STREAM_STOP)
    except Recorder.RecorderError:
        return json_payload({"message": "Could not stop stream"}, True)

    try:
        recorder.request_looper(Recorder.Type.START, 3, 5)
    except Recorder.RecorderError:
        return json_payload({"message": "Could not stop recording"}, True)

    return json_payload({"message": "Recording stopped"})


@api.handle("/criteria")
def criteria_request(event, pathPara, queryPara):
    """Gets all rows and columns from the Criteria table"""
    return get_all(Criteria)


@api.handle("/criteria", httpMethod=MPC_API.POST)
def criteria_insert(event, pathPara, queryPara):
    """Inserts new rows into the criteria table"""
    criteria = Criteria(queryPara["criteria_type"], queryPara["magnitude"], queryPara["duration"])
    database.insert(criteria)
    return json_payload({})


@api.handle("/criteria/{id}")
def criteria_request_by_id(event, pathPara, queryPara):
    """Gets all information from Criteria table based on specified id"""
    return get_by_id(Criteria, pathPara)


@api.handle("/criteria/{id}", httpMethod=MPC_API.DELETE)
def criteria_delete_by_id(event, pathPara, queryPara):
    """Deletes rows from the criteria table based on the specified id"""
    return delete_by_id(Criteria, pathPara)


@api.handle("/criteria/{id}", httpMethod=MPC_API.PUT)
def criteria_update_by_id(event, pathPara, queryPara):
    """Updates the criteria table rows based on the specified id"""
    return update_by_id(Criteria, pathPara, queryPara)


@api.handle("/notification")
def notification_request(event, pathPara, queryPara):
    """Requests notifications from the hardware based on specified notification id"""
    notifications: list[Notification] = database.get_all(Notification)
    for notification in notifications:
        notification.hardware = database.get_hardware_ids_by_notification_id(Hardware_has_Notification,
                                                                             notification.notification_id)
    dict_list = Notification.list_object_to_dict_list(notifications)

    return json_payload(dict_list)


@api.handle("/notification", httpMethod=MPC_API.POST)
def notification_insert(event, pathPara, queryPara):
    """Inserts rows into the notification table"""
    notification = Notification(queryPara[Notification.TYPE], queryPara[Notification.CRITERIA_ID])
    database.insert(notification)
    # id = database.get_id_by_type(Notification, queryPara["notification_type"])
    id = database.get_max_id(Notification)
    if "hardware_id" in queryPara:
        hardware_notification = Hardware_has_Notification(queryPara[Hardware_has_Notification.HARDWARE_ID], id)
        database.insert(hardware_notification)
    return json_payload({"id": id})


@api.handle("/notification/{id}")
def notification_request_by_id(event, pathPara, queryPara):
    """Gets notification from hardware component based on the notification id"""
    id = pathPara["id"]
    notification = database.get_by_id(Notification, id)
    notification.hardware = database.get_hardware_ids_by_notification_id(Hardware_has_Notification,
                                                                         notification.notification_id)
    body = Notification.object_to_dict(notification)

    return json_payload(body)


@api.handle("/notification/{id}", httpMethod=MPC_API.DELETE)
def notification_delete_by_id(event, pathPara, queryPara):
    """Deletes notification from the table based on specified id"""
    return delete_by_id(Notification, pathPara)


@api.handle("/notification/{id}", httpMethod=MPC_API.PUT)
def notification_update_by_id(event, pathPara, queryPara):
    """Updates notification table with new information"""
    return update_by_id(Notification, pathPara, queryPara)


@api.handle("/notification/{id}/add/{hardware_id}", httpMethod=MPC_API.POST)
def notification_insert_hardware(event, pathPara, queryPara):
    """Adds new notification into the into the system from a hardware component"""
    hardware_notification = Hardware_has_Notification(pathPara[Hardware_has_Notification.HARDWARE_ID], pathPara["id"])
    database.insert(hardware_notification)
    return json_payload({})


@api.handle("/notification/{id}/hardware")
def notification_hardware_request(event, pathPara, queryPara):
    """Gets notification from specified hardware component based on id"""
    data = database.get_all_join_field_by_field(Hardware, Hardware_has_Notification,
                                                Hardware.EXPLICIT_HARDWARE_ID,
                                                Hardware_has_Notification.EXPLICIT_HARDWARE_ID,
                                                Hardware_has_Notification.NOTIFICATION_ID, pathPara["id"])
    return json_payload(Hardware.list_object_to_dict_list(data))


@api.handle("/notification/{id}/hardware/{hardware_id}", httpMethod=MPC_API.DELETE)
def notification_hardware_delete_by_id(event, pathPara, queryPara):
    """Deletes notification based on id"""
    return delete_by_hardware_id(Hardware_has_Notification, pathPara)


@api.handle("/resolution")
def resolution_request(event, pathPara, queryPara):
    """Gets all rows and columns from the Resolution table"""
    return get_all(Resolution)


@api.handle("/resolution", httpMethod=MPC_API.POST)
def resolution_insert(event, pathPara, queryPara):
    """Inserts new rows into the resolution table"""
    resolution = Resolution(queryPara[Resolution.NAME], queryPara[Resolution.WIDTH], queryPara[Resolution.HEIGHT])
    database.insert(resolution)
    return json_payload({})


@api.handle("/resolution/{name}")
def resolution_request_by_name(event, pathPara, queryPara):
    """Gets rows from Resolution table based on name"""
    return get_by_name(Resolution, pathPara)


@api.handle("/resolution/{name}", httpMethod=MPC_API.DELETE)
def resolution_delete_by_id(event, pathPara, queryPara):
    """Deletes rows from Resolution table based on id"""
    return delete_by_name(Resolution, pathPara)


@api.handle("/resolution/{name}", httpMethod=MPC_API.PUT)
def resolution_update_by_id(event, pathPara, queryPara):
    """Updates the resolution table based on specified id"""
    return update_by_id(Notification, pathPara, queryPara)


@api.handle("/saving_policy")
def saving_policy_request(event, pathPara, queryPara):
    """Gets saving policy based on saving policy id"""
    saving_policies = database.get_all(Saving_Policy)
    for policy in saving_policies:
        policy.hardware = database.get_hardware_ids_by_saving_policy_id(Hardware_has_Saving_Policy,
                                                                        policy.saving_policy_id)
    dict_list = Saving_Policy.list_object_to_dict_list(saving_policies)

    return json_payload(dict_list)


@api.handle("/saving_policy", httpMethod=MPC_API.POST)
def saving_policy_insert(event, pathPara, queryPara):
    """Inserts new saving policy into saving_policy table"""
    saving_policy = Saving_Policy(queryPara[Saving_Policy.MAX_TIME], queryPara[Saving_Policy.RESOLUTION_NAME])
    database.insert(saving_policy)
    return json_payload({})


@api.handle("/saving_policy/{id}")
def saving_policy_request_by_id(event, pathPara, queryPara):
    """Gets saving policy based on specified id"""
    id = pathPara["id"]
    saving_policy = database.get_by_id(Saving_Policy, id)
    saving_policy.hardware = database.get_hardware_ids_by_saving_policy_id(Hardware_has_Saving_Policy,
                                                                           saving_policy.saving_policy_id)
    body = Saving_Policy.object_to_dict(saving_policy)

    return json_payload(body)


@api.handle("/saving_policy/{id}", httpMethod=MPC_API.DELETE)
def saving_policy_delete_by_id(event, pathPara, queryPara):
    """Deletes saving policy based on specified id"""
    return delete_by_id(Saving_Policy, pathPara)


@api.handle("/saving_policy/{id}", httpMethod=MPC_API.PUT)
def saving_policy_update_by_id(event, pathPara, queryPara):
    """Updates saving policy table based on id"""
    return update_by_id(Saving_Policy, pathPara, queryPara)


@api.handle("/saving_policy/{id}/add/{hardware_id}", httpMethod=MPC_API.POST)
def saving_policy_add_hardware(event, pathPara, queryPara):
    """Adds hardware component to the saving policy table based on hardware id"""
    saving_policy = Hardware_has_Saving_Policy(pathPara["hardware_id"], pathPara["id"])
    database.insert(saving_policy)
    return json_payload({})


@api.handle("/saving_policy/{id}/hardware")
def saving_policy_hardware_request(event, pathPara, queryPara):
    """Gets information from saving policy and hardware table with a join"""
    data = database.get_all_join_field_by_field(Hardware, Hardware_has_Saving_Policy,
                                                Hardware.EXPLICIT_HARDWARE_ID,
                                                Hardware_has_Saving_Policy.EXPLICIT_HARDWARE_ID,
                                                Hardware_has_Saving_Policy.SAVING_POLICY_ID, pathPara["id"])
    return json_payload(Hardware.list_object_to_dict_list(data))


@api.handle("/saving_policy/{id}/hardware/{hardware_id}", httpMethod=MPC_API.DELETE)
def saving_policy_hardware_delete_by_id(event, pathPara, queryPara):
    """Deletes saving policy based on the specified hardware id"""
    return delete_by_hardware_id(Hardware_has_Saving_Policy, pathPara)


@api.handle("/email", httpMethod=MPC_API.POST)
def send_email(event, pathPara, queryPara):
    request_body = event["body"]
    return EmailSender.send(request_body["ToMail"], request_body["Subject"], request_body["LetterBody"])


@api.handle("/file/all", httpMethod=MPC_API.POST)
def get_recording_videos(event, pathPara, queryPara):
    import datetime
    token = event["body"]["token"]
    if not database.verify_field(Account, Account.TOKEN, token):
        return json_payload({"message": "Account does not exist"})

    records = database.query(
        f"""Select distinct Account.account_id, Hardware.hardware_id, arn, file_name, Recording.timestamp From Account 
        Inner Join Account_has_Hardware ON  Account_has_Hardware.account_id = Account.account_id
        Inner Join Hardware ON  Account_has_Hardware.hardware_id = Hardware.hardware_id
        Left Join Recording On Hardware.hardware_id = Recording.hardware_id
        WHere token = "{token}";"""
    )
    import datetime
    now = datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")

    recordings = []
    arns = set()
    channel_id_dict = {}
    files = []
    for account_id, hardware_id, arn, file_name, timestamp in records:
        arns.add(arn)
        channel_id_dict[arn] = hardware_id
        if file_name != None:
            timestamp = timestamp.strftime("%Y-%m-%d %H:%M:%S")
            r = Recording(file_name, timestamp, timestamp, account_id=account_id, hardware_id=hardware_id)
            r.arn = arn
            recordings.append(r)
            files.append(file_name)
    arns = list(arns)

    if len(recordings) == 0:
        account_id = database.get_field_by_field(Account, Account.ID, Account.TOKEN, token)
    else:
        account_id = recordings[0].account_id

    video_retriever = VideoRetriever(settings.BUCKET)
    converted_files = video_retriever.converted_streams(arns)
    for file in converted_files:
        if file not in files:
            files.append(file)
            recordings.append(Recording(file, now, now))

    id_to_folder_stream_list_map = video_retriever.unregistered_stream_map_from_channels(recordings, channel_id_dict)
    for id in id_to_folder_stream_list_map:
        for folder in id_to_folder_stream_list_map[id]:
            if len(id_to_folder_stream_list_map[id][folder]) == 1:
                id_to_folder_stream_list_map[id].pop(folder)
                break
            elif len(id_to_folder_stream_list_map[id][folder]) > 1:
                id_to_folder_stream_list_map[id][folder].pop()

    for id in id_to_folder_stream_list_map:
        for folder in id_to_folder_stream_list_map[id]:
            recordings.append(Recording(folder, None, None))

    video_retriever.convert_stream_in_account(database, account_id, id_to_folder_stream_list_map)

    return json_payload({
        "files": [
            {
                "file_name": f.file_name,
                "url": video_retriever.pre_signed_url_get(f"{settings.CONVERTED}/{f.file_name}/0.mp4",
                                                          expire=3600) if f.timestamp is not None else now,
                "timestamp": f.timestamp if f.timestamp is not None else now,
                "thumbnail": video_retriever.pre_signed_url_get(video_retriever.get_thumbnail_key(f.file_name),
                                                                3600) if f.timestamp is not None else now
            } for f in recordings]
    })


@api.handle("/convert", httpMethod=MPC_API.POST)
def convert_data(event, pathPara, queryPara):
    keys = event["body"]["keys"]
    title = event["body"]["title"]
    VideoRetriever(settings.BUCKET).convert_video(title, keys)
    return json_payload({"message": "Success"})


if __name__ == "__main__":
    # database.insert(Notification(10000, criteria_id=3), ignore=True)

    event = {
        "resource": "/account/signin",
        "httpMethod": MPC_API.POST,
        "body": """{
            "username": "John Smith",
            "password": "Password",
            "email": "default@temple.edu",
            "code": "658186",
            "token": "b442f59cb6126563024fedfbd7fbf1fd",
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
    token = json.loads(response["body"])["token"]
    print(token)

    event = {
        "resource": "/file/all",
        "httpMethod": MPC_API.POST,
        "body": "{\"token\":\"" + token + "\"}",
        "pathParameters": {
            "key": "sample.txt"
        },
        "queryStringParameters": {
            "notification_type": 10
        }
    }
    response = lambda_handler(event, None)

    print(response)
    # database.insert(Account_has_Hardware(18, 36))
    # database.insert(Recording("HCBh4loJzOvw/2023-4-22-23-5-CqEzvvmfv15Q", account_id=18, hardware_id=29))
    # database.insert(Recording("HCBh4loJzOvw/2023-4-22-23-7-L31x4uLipprO", account_id=18, hardware_id=29))
    recording = database.get_all(Recording)
    for r in recording:
        print(r)