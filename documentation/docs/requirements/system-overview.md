---
sidebar_position: 1
---

# System Overview

Our system is made up of three components: Andorid app, Cloud server, and Raspbery pi hardware. 

The Raspberry Pi will host the camera and other necessary hardware accessories (microphone, power cable, sd card).

The MPC app, built in Android Studios, is only compatible with Android devices. With the app, users can remotely access their camera. The initial connection between the Raspberry pi and the app will be established through bluetooth. With the initial bluetooth connection, the user will be able to connect the camera to the Wi-Fi using their phone, as the raspberry pi is a headless system meaning that it does not have a monitor or keyboard attached. Using the app, users can also save videos, audios, and captures. They can also preset their preferences and settings to cater specifically towards their needs. The app will be used both for setting up an initial connection with the device, and for managing the camera’s features. Android studio was used to program the actual application functions, user interface, buttons, and everything that the user will see. 

The AWS Lambda cloud system will be coded python using RDS for a database and Amazon S3 for storage. The cloud will also be accessible using the client devices via wifi. AWS Lambda code will access the RDS database via simple SQL queries to send and retrieve data. This data will then be sent over wifi to be displayed on the user device in the format specified by the application.  
