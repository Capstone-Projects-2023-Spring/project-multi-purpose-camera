package mpc;

/**
 * mpc.ThermalVision is one of the utilities of the multi-purpose-camera.
 * It uses heat sensors to detect hot objects, mostly living things, 
 * and provides many uses for the system.
 */
public abstract class ThermalVision extends RaspberryPi{
    /**
     * temperature represents the temperature picked up by the heat sensing device
     */
    private int temperature;
    /**
     * data_processing processes the data picked up the heat sensor
     */
    public abstract void data_processing( int data[] );
    /**
     * 
     */
    public abstract int[] thermal_vision_conversion();
}
