#Parser uses \n as delimiter
"""
Uses the newline character \\n as a delimiter and will tokenize the string to
get wifi name, password, and new name for the device
"""

import subprocess
import time
import os.path

def wifi_setup(ssid,password):
    # path for wpa_supplicant configuration file 
    wpa_supplicant_path = "/etc/wpa_supplicant/wpa_supplicant.conf"
    
#     cmd = "sudo rm '/home/mpc/Capstone/project-multi-purpose-camera/Raspberry Pi/wpa_supplicant.conf.temp'"
#     #command = "sudo echo -e network={{\\n  ssid=\\"{ssid}\\"\\n  psk=\\"{password}\\"\\n}}" >> "/home/mpc/Capstone/project-multi-purpose-camera/Raspberry Pi/wpa_supplicant.conf""
#     
#     subprocess.run(cmd, shell=True)
    
    """Writes new wifi info to a text file"""
    with open("/home/mpc/project-multi-purpose-camera/Raspberry Pi/bluetooth/temp.txt", "w+") as wifi:
        wifi.write("ctrl_interface=DIR=/var/run/wpa_supplicant GROUP=netdev\n")
        wifi.write("update_config=1\n")
        wifi.write("country=US\n")
        wifi.write("\n")
        wifi.write("network={\n")
        wifi.write('\tssid="%s"\n'%ssid)
        wifi.write('\tpsk="%s"\n'%password)
        wifi.write('\tkey_mgmt=WPA-PSK\n')
        wifi.write("}\n")

#         wifi.write('# interfaces(5) file used by ifup(8) and ifdown(8)\n\n')
#         wifi.write('# Please note that this file is written to be used with dhcpcd\n')
#         wifi.write("# For static IP, consult /etc/dhcpcd.conf and 'man dhcpcd.conf'\n")
#         wifi.write('# Include files from /etc/network/interfaces.d:\n')
#         wifi.write('source-directory /etc/network/interfaces.d\n' )
#         wifi.write('auto wlan0\n')
#         wifi.write('iface wlan0 inet dhcp\n')
#         wifi.write('wpa-ssid "' + ssid + '"\n')
#         wifi.write('wpa-psk "' + password + '"\n')
# 

    # copies text file to a temp wpa_supplicant"""
    cmd = "sudo cp temp.txt wpa_supplicant.conf.temp"
    subprocess.run(cmd,shell=True)
    
    # copies the temp wpa_supplicant to the actual wpa_supplicant that the pi will use"""
    cmd = "sudo cp wpa_supplicant.conf.temp /etc/wpa_supplicant/wpa_supplicant.conf"
    subprocess.run(cmd,shell=True)
    
    # applies the new wifi credentials 
    cmd = "sudo wpa_cli -i wlan0 reconfigure"
    subprocess.run(cmd,shell=True)

    # sleeps so the pi has enough time to reconfigure
    time.sleep(10)
    
    cmd = "sudo wpa_cli -i wlan0 status"
    output = subprocess.check_output(cmd,shell=True)
    # time.sleep(2)
    result = 'COMPLETED'
    decode_output = output.decode()
    # print(decode_output)
    
    # checks if the change was successful and returns the hostname
    if result in decode_output:
        return "Connected to " + ssid + "\n"
    
    return ""
    
"""Checks if there is a device_id saved onto the mahcine
    Parameter: string (new device_id)
    Return: string (new or device_id given by the user from the app)

"""
def device_id_setup(string):
    #print(string)
    result = ""
    #if new device send new
        #create new file for device id and different file for stream info
    #if registered send device_id
    if (os.path.isfile("/home/mpc/project-multi-purpose-camera/Raspberry Pi/id_and_stream/device_id.txt")):
        if len(string) > 2:
            with open("/home/mpc/project-multi-purpose-camera/Raspberry Pi/id_and_stream/device_id.txt", "w") as file_device_id:
                file_device_id.write(string)
    
    if (os.path.isfile("/home/mpc/project-multi-purpose-camera/Raspberry Pi/id_and_stream/device_id.txt")):
        with open("/home/mpc/project-multi-purpose-camera/Raspberry Pi/id_and_stream/device_id.txt", "r") as file_device_id:
            result = file_device_id.read()
    else:
        with open("/home/mpc/project-multi-purpose-camera/Raspberry Pi/id_and_stream/device_id.txt", "w") as file_device_id:
            file_device_id.write("new")
    
    
    return result
    
    
def parser(byte_string):
    # recieves byte string and splits it into an array to be parsed
    
    result = ""
    # the format of the byte string should be wifi\nhostname\npassword
    string_list = byte_string.decode().split('\n')
     
    option = string_list[0]
    
    string_option = ''.join(option)
    
    if "wifi" in string_option:
        # gets the hostname
        ssid = string_list[1:2]
        # gets the password
        password = string_list[2:3]
        # converts the bytes into strings  
        string_ssid = ''.join(ssid)
        string_password = ''.join(password)
        #print(len(string_ssid))
        #print(string_password)
        
        #sends the results to the wifi setup to change the wifi settings
        result = wifi_setup(string_ssid,string_password)
        # print(result)
        
    if "device_id" in string_option:
        deviceID = string_list[1:2]
        deviceID = ''.join(deviceID)
        
        #print(deviceID)
        result = device_id_setup(deviceID)
        
    
    return result
    
   
if __name__ == "__main__":
    byte_string = b'device_id\nCOMPUTER 7084\nAC24dc34!\n'
    temp = parser(byte_string)
    print(temp)
