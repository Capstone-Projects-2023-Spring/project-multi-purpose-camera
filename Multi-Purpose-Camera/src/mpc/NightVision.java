package mpc;

/**
 * mpc.NightVision is one of the utilities of the Multi-Purpose-Camera.
 * It uses the night vision feature of the camera to be able to record
 * stuff better in the dark.
 */
public abstract class NightVision extends RaspberryPi{
    /**
     * light_exposure_level represents how much light is being picked up
     * by the camera.
     */
    private int light_exposure_level;
    /**
     * light_level is used to pick up how much light is being flooded into the camera
     */
    public abstract int light_level( int lightLevel );
}
