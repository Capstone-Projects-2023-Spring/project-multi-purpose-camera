---
sidebar_position: 2
---

# Tasks
_The required hardware and software to be used to develop the project. This includes the selected IDE, compilers, editors, test tools, etc. Map the effort of hardware and software setting up as tasks as well and mark your chart for the completion of such tasks._

## Required Hardware
1. Raspberry Pi
2. Raspberry Pi Camera with Night Vision
3. MicroSD reader & MicroSD card
4. Microphone

## Required Services
1. Amazon Web Service Cloud (AWS)
* Amazon Elastic Compute Cloud (EC2)
* AWS Lambda
* Amazon API Gateway
* Amazon Relational Database Service (RDS)
* Amazon Simple Storage Service (S3)
* Amazon Web Services (AWS) has been utilized to establish the cloud environment for this project. The active server will be set up using AWS Elastic Compute Cloud (EC2), which is a virtual instance of Linux on the AWS cloud. The RestAPI server will be hosted online with AWS Lambda, and the server connection will be managed by API Gateway. The Restful API server is linked to AWS Relational Database Service (RDS) to store user and system data and AWS Simple Storage Service (S3) to store file data.
2. Java with Android SDK
	The Java programming language is a prerequisite for this project as it is utilized to develop the Android application. This language is primarily employed in conjunction with the Android SDK, which is the Standard Development Kit for Android application development. The SDK provides a comprehensive framework for interacting with Android application development tools.

Python 3.8 or above
The Python programming language is utilized to develop the device code on MPC camera devices that operate on the Raspberry Pi, as well as the server code on AWS EC2 server and AWS Lambda Restful API with API Gateway. The OOP design of this language is highly suitable for this project, which requires collaborative efforts from multiple programmers. The OOP design allows for efficient task division and optimized utilization of workflow and time, thereby meeting the project's requirements.

MySQL 8.0
MySQL Workbench
MySQL Connector for Python
The Python programming language is utilized for developing the device code on MPC camera devices that operate on the Raspberry Pi, and also for the server code on AWS EC2 server and AWS Lambda Restful API with API Gateway. The language's OOP design is highly suitable for this project, which requires collaborative efforts from multiple programmers. It enables efficient division of tasks and optimized utilization of workflow and time, fulfilling the project's requirements.
		
Selected IDE
Visual Studios
	Visual Studio is primarily utilized for server-side development. As the server code is executed on AWS, it's necessary to establish a means of interfacing with the server instance from a local machine. Visual Studio includes a plugin known as Remote SSH, which facilitates connection to the AWS EC2 server via SSH and interaction with the virtual computer hosted on AWS. Additionally, Visual Studio offers a range of plugins that may aid in expediting development processes.

Android Studios
	Android Studio is an integrated development environment (IDE) specifically designed for developing Android applications. It is built on the IntelliJ IDEA IDE and operates on the Android operating system. The IDE offers a comprehensive suite of tools for designing, constructing, and testing Android applications. Android Studio includes a range of useful Android development tools, such as the Android emulator, Android SDK (Standard Development Kit) with multiple versions, and a layout editor.

PyCharm
PyCharm is an integrated development environment (IDE) for Python application development. It offers a python interpreter execution environment, python code debugger, and a code editor with python snippets. This greatly facilitates the development of server code and device code in Python in an efficient manner. PyCharm also integrates several third-party tools, such as Git and draw.io, which help to focus on code development without worrying about managing code or creating diagrams from classes.

Compilers
Visual Studio
Android Studios

Test Tools
pytest
Robot Framework
Cucumber
