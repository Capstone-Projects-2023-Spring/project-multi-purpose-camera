try:
    from Database.Data.Data import Data
except:
    from Lambda.Database.Data.Data import Data


class Hardware(Data):
    """Manages the hardware components of the system"""
    TABLE = "Hardware"
    """Specifies the hardware table name"""
    ID = "hardware_id"
    """Specifies the hardware_id attribute column name"""
    RESOLUTION_NAME = "max_resolution"
    """Specifies the max_resolution attribute column name"""
    CHANNEL_NAME = "channel_name"
    """Specifies the channel_name attribute column name"""
    DEVICE_ID = "device_id"
    """Specifies the device_id attribute column name"""
    NAME = "device_name"
    """Specifies the device_name attribute column name"""
    ARN = "arn"
    """Specifies the arn attribute column name"""
    STREAM_KEY = "stream_key"
    """Specifies the stream_key attribute column name"""
    INGEST_ENDPOINT = "ingest_endpoint"
    """Specifies the ingest_endpoint attribute column name"""
    PLAYBACK_URL = "playback_url"
    """Specifies the playback_url attribute column name"""
    S3_RECORDING_PREFIX = "s3_recording_prefix"
    """Specifies the s3_recording_prefix attribute column name"""
    COLUMNS = [ID, RESOLUTION_NAME, CHANNEL_NAME, DEVICE_ID, NAME, ARN, STREAM_KEY, INGEST_ENDPOINT, PLAYBACK_URL, S3_RECORDING_PREFIX]
    """organizes the name, id, account id, and resolution name into an array"""
    EXPLICIT_ID = f"{TABLE}.{ID}"
    """Creates an explicit version of the id variable column name"""
    EXPLICIT_HARDWARE_ID = EXPLICIT_ID
    """Creates an explicit version of the hardware id variable column name"""
    EXPLICIT_RESOLUTION_NAME = f"{TABLE}.{RESOLUTION_NAME}"
    """Creates an explicit version of the resolution name variable column name"""
    EXPLICIT_CHANNEL_NAME = f"{TABLE}.{CHANNEL_NAME}"
    """Creates an explicit version of the device_name attribute column name"""
    EXPLICIT_NAME = f"{TABLE}.{NAME}"
    """Creates an explicit version of the device_name attribute column name"""
    EXPLICIT_DEVICE_ID = f"{TABLE}.{DEVICE_ID}"
    """Creates an explicit version of the device_id name variable column name"""
    EXPLICIT_ARN = f"{TABLE}.{ARN}"
    """Creates an explicit version of the arn name variable column name"""
    EXPLICIT_STREAM_KEY = f"{TABLE}.{STREAM_KEY}"
    """Creates an explicit version of the stream_key name variable column name"""
    EXPLICIT_INGEST_ENDPOINT = f"{TABLE}.{INGEST_ENDPOINT}"
    """Creates an explicit version of the ingest_endpoint name variable column name"""
    EXPLICIT_PLAYBACK_URL = f"{TABLE}.{PLAYBACK_URL}"
    """Creates an explicit version of the playback_url name variable column name"""
    EXPLICIT_S3_RECORDING_PREFIX = f"{TABLE}.{S3_RECORDING_PREFIX}"
    """Creates an explicit version of the s3_recording_prefix name variable column name"""
    EXPLICIT_COLUMNS = [EXPLICIT_ID, EXPLICIT_RESOLUTION_NAME, EXPLICIT_DEVICE_ID, EXPLICIT_NAME, EXPLICIT_ARN,
                        EXPLICIT_STREAM_KEY, EXPLICIT_INGEST_ENDPOINT, EXPLICIT_PLAYBACK_URL,
                        EXPLICIT_S3_RECORDING_PREFIX]
    """Organizes the explicit versions of the variables above into an array"""

    def __init__(self, device_name: str, max_resolution: str, channel_name: str,
                 arn: str, stream_key: str, ingest_endpoint: str,
                 playback_url: str, device_id: str = "md5(ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000))",
                 s3_recording_prefix: str = None, hardware_id: int = None):
        """Initializes the name, max resolution, hardware id, and account id variables"""
        self.channel_name = channel_name
        """channel_name     : string<   Store the channel_name of the hardware"""
        self.device_name = device_name
        """device_name      : string<   Store the device_name of the hardware"""
        self.max_resolution = str(max_resolution)
        """max_resolution   : string<   Store the max_resolution of the hardware"""
        self.device_id = str(device_id)
        """device_id        : string<   Store the device_id of the hardware"""
        self.arn = str(arn)
        """arn              : string<   Store the arn of the hardware"""
        self.stream_key = str(stream_key)
        """stream_key       : string<   Store the stream_key of the hardware"""
        self.ingest_endpoint = str(ingest_endpoint)
        """ingest_endpoint  : string<   Store the ingest_endpoint of the hardware"""
        self.playback_url = str(playback_url)
        """playback_url     : string<   Store the playback_url of the hardware"""
        self.s3_recording_prefix = str(s3_recording_prefix)
        """s3_recording_prefix  : string<   Store the s3_recording_prefix of the hardware"""
        self.hardware_id = int(hardware_id) if hardware_id is not None else None
        """hardware_id  : int<      Specify the hardware_id of the hardware (Optional)."""

    def __str__(self):
        """Returns a formatted string of the variables"""
        return "[Hardware    ]              :MAX_RESOLUTION: {:<12} HARDWARE_ID:{:<12} " \
               "CHANNEL_NAME: {:<12} DEVICE_NAME:{:<12} DEVICE_ID:{:<12} " \
               "ARN:{:<12} STREAM_KEY:{:<12} INGEST_ENDPOINT:{:<12} PLAYBACK_URL:{:<12} " \
               "S3_RECORDING_PREFIX:{:<12}".format(
            self.max_resolution, str(self.hardware_id), self.channel_name, self.device_name, self.device_id, self.arn,
            self.stream_key, self.ingest_endpoint, self.playback_url, self.s3_recording_prefix
        )

    @staticmethod
    def dict_to_object(payload: dict, explicit=False) -> "Hardware":
        """
            Determines if explicit is true, if so create Account with the explicit variables column name with payload,
            if not create Hardware with the non-explicit variables column name with payload and return object

            Parameter:

                >payload    : dict<:    Payload that contains the information of hardware creation
                >explicit   : bool<:    Flag that indicates the payload key is explicit column

            Return:

                >hardware    : Hardware<: Hardware created
        """
        if explicit:
            return Hardware(payload[Hardware.EXPLICIT_NAME], payload[Hardware.EXPLICIT_RESOLUTION_NAME],
                            payload[Hardware.EXPLICIT_CHANNEL_NAME], payload[Hardware.EXPLICIT_ARN],
                            payload[Hardware.EXPLICIT_STREAM_KEY], payload[Hardware.EXPLICIT_INGEST_ENDPOINT],
                            payload[Hardware.EXPLICIT_PLAYBACK_URL], payload[Hardware.EXPLICIT_DEVICE_ID],
                            payload[Hardware.EXPLICIT_S3_RECORDING_PREFIX], payload[Hardware.EXPLICIT_ID])
        else:
            return Hardware(payload[Hardware.NAME], payload[Hardware.RESOLUTION_NAME],
                            payload[Hardware.CHANNEL_NAME], payload[Hardware.ARN],
                            payload[Hardware.STREAM_KEY], payload[Hardware.INGEST_ENDPOINT],
                            payload[Hardware.PLAYBACK_URL], payload[Hardware.DEVICE_ID],
                            payload[Hardware.S3_RECORDING_PREFIX], payload[Hardware.ID])

