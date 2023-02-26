package mpc;

/*
 * mpc.MorningAlarm is one of the utilities of the Multi-Purpose-Camera.
 * It is used as an alarm system to help the user wake up in the morning.
 */
public abstract class MorningAlarm extends NightVision{
    /*
     * light_exposure_levels represents the intensity of the amount of light
     * picked up by the camera
     */
    private int light_exposure_levels;
    /*
     * measure_light_levels is the function used to measure the amount of light
     * picked up by the camera and store it into Light_Exposure_Levels.
     */
    public abstract void measure_light_levels( int levels );
    /*
     * sunrise uses the camera to see if the sun is coming up and if light is
     * flooding the camera.
     */
    public abstract String sunrise( int sun );
}
