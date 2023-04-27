import bluetooth
import time
from bluetooth_parser import parser

"""Simple bluetooth server code to wait for user to connect using rfcomm on port 1"""
serverAddr = 'E4:5F:01:AF:19:30'

###Sets up bluetooth server using rfcomm on port 1
server_socket=bluetooth.BluetoothSocket( bluetooth.RFCOMM )
port = 1
server_socket.bind(("",port))
server_socket.listen(1)
#DC:A6:32:03:E5:7A
epoch = time.time()

###This nested while loop waits for a connection then receives data from the client
while True:
    ###waits for and accepts client
    print("waiting for connection")
    client_socket, address = server_socket.accept()
    print("Accepted connection from", address)
    # mac address of pi --> DC:A6:32:03:E5:7A
    client_socket.send("E4:5F:01:AF:19:30\n")
    
    ###recieves data from the client in try catch so it can connect again if there is a disconnection
    while True:
        try:
            data = client_socket.recv(1024)
            if not data:
                break
            print("Received:" , data)
            result = parser(data)
            if (len(result) < 2):
                client_socket.send("Wifi credentials are incorrect try again.\n")
            else:
                client_socket.send("Connected to " + result + "\n")
        except bluetooth.btcommon.BluetoothError:
            print("Client disconnected\n")
            break
    ###Ends the program after a certain amount of seconds
#     elapse = time.time() - epoch
#     if elapse >= 300:
#         print("Bluetooth data transfer service is finished")
#         break

client_socket.close()
server_socket.close()
