package mpc;

/**
 * mpc.BabyMonitor is one of the utilities of the multi-purpose-camera.
 * It acts as a baby monitor by monitoring the intensity of the sound waves 
 * using the audio device.
 */
public abstract class BabyMonitor extends HomeSecurity{
    /**
     * This Max_Decibels is the maximum number of decibels needed for a notification
     * to go to the user
     */
    private int max_decibels;

    /** 
     * is_Max_Decibel determines if the maximum number of decibels has been reached
    */
    public abstract boolean is_max_decibel();
}
