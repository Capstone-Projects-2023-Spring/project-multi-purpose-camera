## Keywords

- Adroid mobile app
- Raspberry pi
- Camera
- AWS
- MySQL Database

<!--Section #, as well as any words that quickly give your peers insights into the application like programming language, development platform, type of application, etc.-->

## Project Abstract

This document proposes a multiple purpose camera that has multiple functions which can adapt to the user's needs depending on the situation. The system will be built using a raspberry pi with camera and microphone attachments. Features include regular camera capturing, thermal mode, night vision mode, sound detection, motion detection, and light detection. The camera will connect to mobile devices and the cloud via wifi and bluetooth. 

## High Level Requirement

<!--Describe the requirements – i.e., what the product does and how it does it from a user point of view – at a high level.-->
The multi purpose camera is a product that can be used in a variety of ways. Essentially, it is a camera that can video record, capture, and livestream that can be used for whatever the user needs it for (ie. baby monitor, car dashcam, door cam, etc.). Using the app, users will initially connect the camera to their device via bluetooth. After the initial buletooth connection is established, the user can get their camera connected to their wifi. Once the wifi connection is established, the camera will just need to be set up in the right location, and it is good to go. The app can be used to access stored recordings, camera captures, and livestreams. Camera settings can also be modified and viewed in the app. 



## Conceptual Design

<!--Describe the initial design concept: Hardware/software architecture, programming language, operating system, etc.-->
This project has three working components: the raspberrry pi, the android app, and the server. 

The Raspberry pi needs to be configured with the hardware: camera, microphone, SD card, and power cable. The raspberry pi then needs establish a connection with the device via Bluetooth. This is because the raspberry pi is a headless machince, meaning that it does not have a monitor or keyboard attached which will allow users to enter credentials for wifi connection. Therefore, once the bluetooh connection is established, the device will send wifi configurations packets which will allow the raspberry pi to connect to the wifi. The Raspberry Pi 4 comes with bluetooth and wifi capabliities so no additional hardware/software is necessary to enable that. 

The Android app can be installed on Android devices. The app was coded in Java on Android Studios. Through the app, the user should be able to create an account or login in with previously existing account. Then, the connections for the Raspberry pi and the server can be established. Using the app, the user will be able to view livestream, captures, and audios.

Finally, the server is operated on the back end. In the cloud system. The code in AWS Lambda will be either java using RDS for a database and Amazon S3 for storage. The cloud will also be accessible using the client devices via wifi. The video data will be sent to the cloud and analyzed in the server to reduce the computational work on the client side and take advantage of high performance computers on cloud to speed up the analysis of the video footagethat are able to analyze video footage much faster than any Android phone would be able to. The video data and analyzed result will be sent back to clients when necessary.


## Background

<!--The background will contain a more detailed description of the product and a comparison to existing similar projects/products. A literature search should be conducted and the results listed. Proper citation of sources is required. If there are similar open-source products, you should state whether existing source will be used and to what extent. If there are similar closed-source/proprietary products, you should state how the proposed product will be similar and different.-->

Compared to most products on the market, our products aims to be more multi-functional. With different persona configurations and optimizable design, our product is not only limited to a regular security camera that is pre-designed to be used in a specific way, such as a doorbell monitor, baby monitor, dashcam, etc. Our product can be all these in one. Action cameras such as GoPros can come close to what our product aims to achieve, however, our Multi-Purpose Camera is set apart by our corrsponsing app. Using our app and camera together, the user can easily achieve what they want through the customization options our app offers. 


<b>Resources</b>

- https://github.com/rpellerin/raspberry-pi-home-automation (python version) 
- https://github.com/Ruud14/SecurityCamera (python version) 
- https://github.com/MieszkoMakuch/pi-security-camera (python version) 
- https://github.com/TaylorTheDeveloper/AzureCloudCam (python version) 
- https://www.enostech.com/the-pros-of-using-a-gopro-as-an-everyday-or-travel-camera/#:~:text=One%20of%20the%20main%20drawbacks,entire%20day's%20worth%20of%20adventure.
- https://pyshine.com/Send-video-over-UDP-socket-in-Python/
- https://pyshine.com/Send-video-over-UDP-socket-in-Python/
- https://people.csail.mit.edu/albert/bluez-intro/c212.html

## Required Resources

<!--Discuss what you need to develop this project. This includes background information you will need to acquire, hardware resources, and software resources. If these are not part of the standard Computer Science Department lab resources, these must be identified early and discussed with the instructor.-->
<b>Hardware Requirements</b>

- Raspberry Pi
- Camera
- Microphone
- MicroSD card

<br />
<b>Software Requirements</b>

- Android Studios
- Visual Studios
- PyCharm
- AWS
- MySQL Database
- Android Emulator (or physical Android device will work too)


## Quick Setup:
pip install opencv-python\
pip install imutils\
pip install pybluez\
pip install pyaudio


## Collaborators

[//]: # ( readme: collaborators -start )

<table>
<tr>
    <td align="center">
        <a href="https://github.com/JustinArd">
            <br />
            <sub><b>Justin Ardamoy</b></sub>
        </a>
    </td>
    <td align="center">
        <a href="">
            <br />
            <sub><b>Tim Brennan</b></sub>
        </a>
    </td>
    <td align="center">
        <a href="https://github.com/NickCoffin">
            <br />
            <sub><b>Nicholas Coffin</b></sub>
        </a>
    </td>
    <td align="center">
        <a href="https://github.com/jo-k0806">
            <br />
            <sub><b>Joanne Kim</b></sub>
        </a>
    </td>
    <td align="center">
        <a href="">
            <br />
            <sub><b>Keita Nakashima</b></sub>
        </a>
    </td>
    <td align="center">
        <a href="">
            <br />
            <sub><b>Sid Philips</b></sub>
        </a>
    </td>
    <td align="center">
        <a href="https://github.com/TylerSchemaitat">
            <br />
            <sub><b>Tyler Schemaitat</b></sub>
        </a>
    </td>
    </tr>
</table>

[//]: # ( readme: collaborators -end )
