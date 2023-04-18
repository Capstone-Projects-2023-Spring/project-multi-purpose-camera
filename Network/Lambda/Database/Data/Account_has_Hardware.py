try:
    from Database.Data.Data import Data
except:
    from Lambda.Database.Data.Data import Data


class Account_has_Hardware(Data):
    """Manages the notifications the user gets from the system"""
    TABLE = "Account_has_Hardware"
    """Specifies the Account_has_Hardware table name"""
    ACCOUNT_ID = "account_id"
    """Specifies the account_id attribute column name"""
    HARDWARE_ID = "hardware_id"
    """Specifies the hardware_id attribute column name"""
    COLUMNS = [HARDWARE_ID, ACCOUNT_ID]
    """Organizes the hardware id and account id into an array"""
    EXPLICIT_ACCOUNT_ID = f"{TABLE}.{ACCOUNT_ID}"
    """Creates an explicit version of the account id variable column name"""
    EXPLICIT_HARDWARE_ID = f"{TABLE}.{HARDWARE_ID}"
    """Creates an explicit version of the hardware id variable column name"""
    EXPLICIT_COLUMNS = [EXPLICIT_HARDWARE_ID, EXPLICIT_ACCOUNT_ID]
    """Organizes the explicit version of the variables above into an array"""

    def __init__(self, account_id: int, hardware_id: int):
        """Initializes the hardware id and notification id variables"""
        self.account_id = int(account_id)
        """account_id       : int<      Specify the account_id of the object."""
        self.hardware_id = int(hardware_id)
        """hardware_id      : int<      Specify the hardware_id of the object."""

    def __str__(self):
        """Returns a formatted string version of the variables"""
        return "[Account_has_Hardware   ]               :ACCOUNT_ID: {:<12} HARDWARE_ID: {:<8}" \
            .format(self.account_id, self.hardware_id)

    @staticmethod
    def dict_to_object(payload: dict, explicit=False) -> "Account_has_Hardware":
        """
            Determines if explicit is true, if so create Account_has_Hardware with the explicit variables column name with payload,
            if not create Hardware with the non-explicit variables column name with payload and return object

            Parameter:

                >payload    : dict<:    Payload that contains the information of object creation
                >explicit   : bool<:    Flag that indicates the payload key is explicit column

            Return:

                >object    : Account_has_Hardware<: object created
        """
        if explicit:
            return Account_has_Hardware(
                payload[Account_has_Hardware.EXPLICIT_ACCOUNT_ID],
                payload[Account_has_Hardware.EXPLICIT_HARDWARE_ID])
        else:
            return Account_has_Hardware(
                payload[Account_has_Hardware.ACCOUNT_ID],
                payload[Account_has_Hardware.HARDWARE_ID])



