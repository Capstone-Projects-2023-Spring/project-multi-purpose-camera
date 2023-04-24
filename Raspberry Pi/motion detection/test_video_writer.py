from picamera2 import Picamera2
import cv2

picam = Picamera2()
picam.configure(picam.create_preview_configuration(main={"format": 'XRGB8888', "size": (640,480)}))
picam.start()


RECORD_DURATION = 10
VIDEO_PATH = '/home/mpc/Videos'
fourcc = cv2.VideoWriter_fourcc(*'mp4v')
fps = 20
frame_size = (640, 480)
#video_writer = cv2.VideoWriter('test.avi', cv2.VideoWriter_fourcc(*'MPEG'), fps, frame_size)

"""Creates a video writer for when motion is detected"""
def create_video_writer():
    # Create the video file name using the current date and time
    #timestamp = datetime.datetime.now().strftime("%Y-%m-%d_%H-%M-%S")
    #video_path = os.path.join(VIDEO_PATH, f"motion_{timestamp}.avi")
    # Create the video writer object

    
    return cv2.VideoWriter("test.mp4", fourcc, fps, frame_size)

out = create_video_writer()

while True:
    while True:
        frame = picam.capture_array()
        
        
        
        out.write(frame)
        
        cv2.imshow("camera", frame)
        key = cv2.waitKey(1) & 0xFF
        if key == ord('q'):
            break
out.release()
picam.close()
       