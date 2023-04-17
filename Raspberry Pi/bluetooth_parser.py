#Parser uses \n as delimiter
"""
Uses the newline character \\n as a delimiter and will tokenize the string to
get wifi name, password, and new name for the device
"""

import subprocess

def wifi_setup(ssid,password):
    # path for wpa_supplicant configuration file 
    wpa_supplicant_path = "/etc/wpa_supplicant/wpa_supplicant.conf"
    
#     cmd = "sudo rm '/home/mpc/Capstone/project-multi-purpose-camera/Raspberry Pi/wpa_supplicant.conf.temp'"
#     #command = "sudo echo -e network={{\\n  ssid=\\"{ssid}\\"\\n  psk=\\"{password}\\"\\n}}" >> "/home/mpc/Capstone/project-multi-purpose-camera/Raspberry Pi/wpa_supplicant.conf""
#     
#     subprocess.run(cmd, shell=True)
    
    """Writes new wifi info to a text file"""
    with open("/home/mpc/Capstone/project-multi-purpose-camera/Raspberry Pi/temp.txt", "a+") as wifi:
        wifi.write("network={\n")
        wifi.write('\tssid="%s"\n'%ssid)
        wifi.write('\tpsk="%s"\n'%password)
        wifi.write('\tkey_mgmt=WPA-PSK\n')
        wifi.write("}\n")
        
    """copies text file to a temp wpa_supplicant"""
    cmd = "sudo cp temp.txt wpa_supplicant.conf.temp"
    subprocess.run(cmd,shell=True)
    
    """copies the temp wpa_supplicant to the actual wpa_supplicant that the pi will use"""
    cmd = "sudo cp wpa_supplicant.conf.temp /etc/wpa_supplicant/wpa_supplicant.conf"
    subprocess.run(cmd,shell=True)
    
    cmd = "sudo reboot"
    subprocess.run(cmd,shell=True)
    
    

def wifi_parser(byte_string):
    string_list = byte_string.decode().split('\n')
    
    ssid = string_list[1:2]
    password = string_list[2:3]
    
    string_ssid = ''.join(ssid)
    string_password = ''.join(password)
#     print(string_ssid)
#     print(string_password)
    wifi_setup(string_ssid,string_password)
    
    
   
if __name__ == "__main__":
    byte_string = b'wifi\nCOMPUTER 7084\nAC24dc34!\n'
    wifi_parser(byte_string)