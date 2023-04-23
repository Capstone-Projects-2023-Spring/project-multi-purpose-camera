import cv2

# Initialize webcam
cap = cv2.VideoCapture(0)

# Initialize frame variables
frame = None
gray = None
previous_gray = None

# Initialize motion detection parameters
delta_thresh = 5
blur_size = 7
min_area = 1200

# Loop over frames from the webcam
while True:
    # Read a frame from the webcam
    ret, frame = cap.read()

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
    cv2.imshow("Security Feed", frame)

    # If the 'q' key is pressed, stop the loop
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

    # Set the current frame as the previous frame for the next iteration
    previous_gray = gray

# Release the webcam and close all windows
cap.release()
cv2.destroyAllWindows()
