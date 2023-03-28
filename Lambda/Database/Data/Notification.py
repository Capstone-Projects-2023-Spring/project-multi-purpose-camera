class Notification:
    TABLE = "Notification"
    TYPE = "notification_type"
    ID = "notification_id"
    CRITERIA_TYPE = "criteria_type"
    HARDWARE_ID = "hardware_id"
    COLUMNS = [TYPE, CRITERIA_TYPE, HARDWARE_ID, ID]
    EXPLICIT_TYPE = f"{TABLE}.{TYPE}"
    EXPLICIT_ID = f"{TABLE}.{ID}"
    EXPLICIT_CRITERIA_TYPE = f"{TABLE}.{CRITERIA_TYPE}"
    EXPLICIT_HARDWARE_ID = f"{TABLE}.{HARDWARE_ID}"
    EXPLICIT_COLUMNS = [EXPLICIT_TYPE, EXPLICIT_ID, EXPLICIT_CRITERIA_TYPE, EXPLICIT_HARDWARE_ID]

    def __init__(self, notification_type: str, criteria_type: str, hardware_id: int, notification_id: int = None):
        self.notification_type = notification_type
        self.criteria_type = criteria_type
        self.hardware_id = int(hardware_id)
        self.notification_id = int(notification_id) if notification_id is not None else None

    def __str__(self):
        return "[Notification   ]               :NOTIFICATION_TYPE: {:<12} CRITERIA_TYPE: {:<12} HARDWARE_ID: {:<8} " \
               "NOTIFICATION_ID: {:<12}" \
            .format(self.notification_type, self.criteria_type, self.hardware_id, self.notification_id)

    @staticmethod
    def dict_to_object(payload: dict, explicit=False) -> "Notification":
        if explicit:
            return Notification(payload[Notification.EXPLICIT_TYPE], payload[Notification.EXPLICIT_CRITERIA_TYPE],
                                payload[Notification.EXPLICIT_HARDWARE_ID], payload[Notification.EXPLICIT_ID])
        else:
            return Notification(payload[Notification.TYPE], payload[Notification.CRITERIA_TYPE],
                                payload[Notification.HARDWARE_ID], payload[Notification.ID])

    @staticmethod
    def list_dict_to_object_list(data_list: list[dict], explicit=False) -> list["Notification"]:
        accounts = []
        for account in data_list:
            accounts.append(Notification.dict_to_object(account, explicit))
        return accounts
