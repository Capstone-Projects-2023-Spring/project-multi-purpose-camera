from picamera2 import Picamera2
import cv2

picam = Picamera2()
picam.configure(picam.create_preview_configuration(main={"format": 'XRGB8888', "size": (640,480)}))
picam.start()


while True:
    while True:
        frame = picam.capture_array()
        cv2.imshow("camera", frame)
        key = cv2.waitKey(1) & 0xFF
        if key == ord('q'):
            break
       