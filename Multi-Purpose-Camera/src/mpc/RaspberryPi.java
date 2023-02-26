package mpc; /**
 * Imports java libraries
 */
import java.util.*;
/**
 * mpc.RaspberryPi is the main piece of hardware in this system that
 * will communicate with other devices and code.
 */
public abstract class RaspberryPi{
    /**
     * The wifi used to connect devices
     */
    private String WIFI;
    /**
     * criteria used to determine what is done and when
     */
    private List<Integer> criteria = new ArrayList<Integer>();

    /**
     * Will determine if certain criteria has been met, and what functions and objects
     * will be involved if that happens
     */
    public abstract int criteria_met(List<Integer> criteria);
    /**
     * Saves video recorded by the camera
     */
    public abstract void save_video();
    /**
     * Sends video to destination specified by the user
     */
    public abstract void send_video();
    /**
     * Connects the system to a close device via bluetooth
     */
    public abstract void bluetooth_connection();
    /**
     * Connects system to the internet
     */
    public abstract void WIFI_connection();
    /**
     * Sends alerts and notifications to user if certain criteria have been met
     */
    public abstract void send_alert( int alert );
}