#Parser uses \n as delimiter
"""
Uses the newline character \\n as a delimiter and will tokenize the string to
get wifi name, password, and new name for the device
"""

import subprocess
import time

def wifi_setup(ssid,password):
    # path for wpa_supplicant configuration file 
    wpa_supplicant_path = "/etc/wpa_supplicant/wpa_supplicant.conf"
    
#     cmd = "sudo rm '/home/mpc/Capstone/project-multi-purpose-camera/Raspberry Pi/wpa_supplicant.conf.temp'"
#     #command = "sudo echo -e network={{\\n  ssid=\\"{ssid}\\"\\n  psk=\\"{password}\\"\\n}}" >> "/home/mpc/Capstone/project-multi-purpose-camera/Raspberry Pi/wpa_supplicant.conf""
#     
#     subprocess.run(cmd, shell=True)
    
    """Writes new wifi info to a text file"""
    with open("/home/mpc/project-multi-purpose-camera/Raspberry Pi/temp.txt", "w+") as wifi:
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

    """copies text file to a temp wpa_supplicant"""
    cmd = "sudo cp temp.txt wpa_supplicant.conf.temp"
    subprocess.run(cmd,shell=True)
    
    """copies the temp wpa_supplicant to the actual wpa_supplicant that the pi will use"""
    cmd = "sudo cp wpa_supplicant.conf.temp /etc/wpa_supplicant/wpa_supplicant.conf"
    subprocess.run(cmd,shell=True)
    
    cmd = "sudo wpa_cli -i wlan0 reconfigure"
    subprocess.run(cmd,shell=True)

    time.sleep(10)
    
    cmd = "sudo wpa_cli -i wlan0 status"
    output = subprocess.check_output(cmd,shell=True)
    # time.sleep(2)
    result = 'COMPLETED'
    decode_output = output.decode()
    # print(decode_output)
    if result in decode_output:
        return ssid
    
    return ""
    
    
def wifi_parser(byte_string):
    string_list = byte_string.decode().split('\n')
    
    ssid = string_list[1:2]
    password = string_list[2:3]
#     print(ssid)
#     print(password)
    string_ssid = ''.join(ssid)
    string_password = ''.join(password)
    #print(len(string_ssid))
    #print(string_password)
    
    result = wifi_setup(string_ssid,string_password)
    return result
    
   
if __name__ == "__main__":
    byte_string = b'wifi\nCOMPUTER 7084\nAC24dc34!\n'
    wifi_parser(byte_string)
