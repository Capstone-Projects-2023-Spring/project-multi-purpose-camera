---
sidebar_position: 1
---

# Activities
_requirements gathering, top-level design, detailed design, implementation, integration, test, bug fixing, and optimization_

## Requirements Gathering 
For the Multi-Purpose Camera, it will require the use of a Raspberry Pi along with a Raspberry PI camera capable of Night Vision along with a microphone, which will connect to bluetooth and the wifi on any device. The features for the camera will be programmed in Python. The Cloud service, connected to the Camera and device, is going to be AWS Cloud. In the Cloud, we will have to understand Elastic Compute Cloud (EC2) and its interfaces. EC2 will connect to the MySQL database and Amazon S3 Storage. The device will have an app that will run using Java’s GUI or Kotlin. 

## Top-Level Design
The top-level design of the Multi-Purpose Camera allows for security and personal use where it comes to recording a moment. The user will place down the camera to where they need it to be, adjust it physically, and use their smartphone to interact with the cameras. The App will allow them to create Alerts for the camera to optimize the use.

When the user opens the app, it will display the camera's setup and connection to the phone. Each camera will be identified. They will also see an option to add more cameras along with clips from the cloud and settings. For each camera, there is an option button that when pressed, it will give an option to create an Alert. Each Alert will have a selection of a camera and at least one Criteria affecting the brightness, luminance, a time limiter, and a choice between what type of alarm it could be (notification/alarm). Users can have more than one Criteria if they so choose depending on the situation. 

## Detailed Design
Only the input and output data that meet the criteria or is manually selected will be stored into the MySQL Database. The database will provide a time and date for each video captured. Time and Date are important for the user especially if anything significant happens. The outputs are all going to be videos. 

The front-end design of the App for the Multi-Purpose Camera will be a Java based design. The App is required for devices to connect to the bluetooth and wifi in order to connect with the Cloud and camera. From there, the user can create Alerts for certain cameras and view the recordings from the cloud. 

## Testing
The Multi-Purpose Camera will have a series of test cases for the frontend and backend development. We will utilize integration testing based on the use case diagram. Some of the use case testing is connecting the database with the device and connecting the camera with the wifi. We will also utilize the functional and usability testing which will include a test for capturing a video and sending it to the cloud, viewing a video from your phone in the cloud and setting up criterias for an Alert. Interface testing will determine how easy it is for the user to use the App on their device. 
