package mpc;

/**
 * mpc.DashCam is one of the utilities of the multi-purpose-camera.
 * It acts as a dashcam for the vehical and saves video to the user's
 * device
 */
public abstract class DashCam extends RaspberryPi{
    /**
     * time_stamp is an integer that records a time stamp of the user's choosing
     */
    private int time_stamp;
    /**
     * if_power_start_rec starts determines if the car's power has been turned on
     * and if so, then the recording will start
     */
    public abstract void if_power_start_rec();
    /**
     * local_save saves the recording to a local device via bluetooth or wifi
     */
    public abstract void local_save();
}
