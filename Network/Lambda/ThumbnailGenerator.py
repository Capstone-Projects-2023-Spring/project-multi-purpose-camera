from ffvideo import VideoStream
pil_image = VideoStream('0.flv').get_frame_at_sec(5).image()
pil_image.save('frame5sec.jpeg')