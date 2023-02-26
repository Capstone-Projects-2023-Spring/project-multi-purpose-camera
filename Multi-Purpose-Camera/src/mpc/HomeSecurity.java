package mpc;

/**
 * mpc.HomeSecurity is one of the utilities of the multi-purpose-camera
 * It's meant to alert the user by sending a notification if motion is detected 
 * at the spot where the user puts the camera(namely the front door)
 */
public abstract class HomeSecurity extends RaspberryPi{
    /**
     * decibels is the intensity of the sound waves picked up
     * by the audio device
     */
    private int decibels;
    /**
     * light_exposure_levels is the amount of light exposed to the camera
     */
    private int light_exposure_levels;
    /**
     * decibel_reading is the function used to record the amount of sound detected
     */
    public abstract int decibel_reading();
    /**
     * motion_detection detects motion through the camera
     */
    public abstract void motion_detection();
    /**
     * inactivity_monitor is what monitors the amount of activity picked up by the
     * camera 
     */
    public abstract void inactivity_monitor();
}
