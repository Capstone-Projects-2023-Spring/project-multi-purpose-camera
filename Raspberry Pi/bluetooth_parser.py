#Parser uses \n as delimiter
"""
Uses the newline character \\n as a delimiter and will tokenize the string to
get wifi name, password, and new name for the device
"""

import subprocess

def wifi_setup(ssid,password):
    # path for wpa_supplicant configuration file 
    wpa_supplicant_path = "/etc/wpa_supplicant/wpa_supplicant.conf"
    
    subprocess.run(f'echo -e "network={{\\n  ssid=\\"{ssid}\\"\\n  psk=\\"{password}\\"\\n}}" >> /etc/wpa_supplicant/wpa_supplicant.conf', shell=True)

def wifi_parser(byte_string):
    string_list = byte_string.decode().split('\n')
    
    ssid = string_list[1:2]
    password = string_list[2:3]
    
    print(ssid)
    print(password)
    wifi_setup(ssid,password)
    
    
   
if __name__ == "__main__":
    byte_string = b'wifi\nname\npassword\n'
    wifi_parser(byte_string)