---
sidebar_position: 2Brainstorming
---

# Tasks
_A task is the performance of an activity leading to a specific feature in a product. E.G. Design of unit x. Associated with each task is predecessor tasks (what tasks must be complete before this task can start) an estimated effort estimated finish data responsible individual successor tasks (what tasks cannon start until this task is complete). A task should be completed within 1-2 weeks._

## Planning/Elaboration Phase (PP)
| # | Task | Platform | Estimated Timeframe | Finish Data | Assigned Individual(s) | Successor Task(s) |
|:---:|:---------------:|:----------:|:----------:|:--------------------:|:---------------:|:--------------------:|
| 1 | Brainstorming | - | 1 week | Ideas | Everyone | - |
| 2 | Initial hardware mockup | Raspberry Pi | 1 week | Blueprint/design of hardware | Tim, Nick, Joanne | Building Hardware (Camera / Raspberry Pi) |
| 3 | Initial app mockup | Java | 1 week | Blueprint/design of app | Tyler, Justin | Running app |
| 4 | Initial cloud mockup | AWS | 1 week | Blueprint/design of cloud | Keita, Sid | Cloud connection |
| 5 | Research database | MySQL | 2 week | Database | Keita | Storing recordings |
| 6 | Connection between devices mockup | - | 1 week | understanding of how devices will communicate | Tyler, Joanne | Connection between devices |


## Implementation Phase (IP)

_Camera_
| # | Task | Platform | Estimated Timeframe | Finish Data | Assigned Individual(s) | Successor Task(s) |
|:---:|:---------------:|:----------:|:----------:|:--------------------:|:---------------:|:--------------------:|
| 1 | Connect Raspberry Pi to camera | Raspberry Pi | 1 week | Working camera that can capture/record | Tim, Nick | Connection to other devices
| 2 | Connect Raspberry Pi to microphone | Raspberry Pi | 1 week | Can capture sound | Tim, Nick | Audio settings
| 3 | Connect Raspberry Pi to Device | Bluetooth | 1 week | Using bluetooth to connect to the app to be able to access wifi | Tyler, Justin | App development/Connection to cloud |
| 4 | Camera connection to cloud | Wifi | 1 week | Connection to cloud for video storage | Keita, Sid | Cloud storage and database management |

