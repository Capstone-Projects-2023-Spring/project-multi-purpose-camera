import cv2

from picamera2 import Picamera2

# setup picamera2 camera
picam2 = Picamera2()
picam2.configure(picam2.create_preview_configuration(main={"format": 'XRGB8888', "size": (640,480)}))
picam2.start()

# Initialize frame variables
frame = None
gray = None
previous_gray = None

# Initialize motion detection parameters
delta_thresh = 5
blur_size = 7
min_area = 1000
is_motion = False

# Loop over frames from the webcam
while True:
    # Read a frame from the webcam
    frame = picam2.capture_array()

    # Convert the frame to grayscale
    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    gray = cv2.GaussianBlur(gray, (blur_size, blur_size), 0)

    # If the previous frame is None, initialize it
    if previous_gray is None:
        previous_gray = gray
        continue

    # Compute the absolute difference between the current frame and the previous frame
    frame_delta = cv2.absdiff(previous_gray, gray)
    thresh = cv2.threshold(frame_delta, delta_thresh, 255, cv2.THRESH_BINARY)[1]
    thresh = cv2.dilate(thresh, None, iterations=2)

    # Find contours in the threshold image
    contours, hierarchy = cv2.findContours(thresh.copy(), cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)

    # Loop over contours
    for contour in contours:
        # If the contour is too small, ignore it
        if cv2.contourArea(contour) < min_area:
            continue

        # Compute the bounding box for the contour and draw it on the frame
        (x, y, w, h) = cv2.boundingRect(contour)
        cv2.rectangle(frame, (x, y), (x + w, y + h), (0, 255, 0), 2)

    # Show the frame
    cv2.imshow("Feed", frame)

    # If the 'q' key is pressed, stop the loop
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

    # Set the current frame as the previous frame for the next iteration
    previous_gray = gray

# Release the webcam and close all windows
picam2.close()
cv2.destroyAllWindows()
