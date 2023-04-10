package com.example.layout_version;

import android.widget.ArrayAdapter;

import java.util.ArrayList;


/**
 * The class Saving_ policy implements displayable_ policy. This sets the saving policy for the app.
 */
public class Saving_Policy implements Displayable_Policy{
    public int id;
    private int max_time;

    /**
     *
     * Get max time
     *
     * @return the _max_time
     */
    public int get_max_time(){

        return max_time;
    }
    private ArrayList<Camera> cameras;

    /**
     *
     * Get cameras
     *
     * @return the _cameras
     */
    public ArrayList<Camera> get_cameras(){

        ArrayList<Camera> result = new ArrayList<>();
        for(int i = 0; i < cameras.size(); i++){
            result.add(cameras.get(i));
        }
        return result;
    }
    private Resolution resolution;






    /**
     *
     * Get resolution
     *
     * @return the _resolution
     */
    public Resolution get_resolution(){

        return resolution;
    }


    /**
     *
     * Saving policy
     *
     * @param parent  the parent.
     * @param time  the time.
     * @param resolution  the resolution.
     * @param id  the id.
     */
    public Saving_Policy(Camera parent, int time, Resolution resolution, int id){

        this.resolution = resolution;
        max_time = time;
        cameras = new ArrayList<>();
        cameras.add(parent);
        this.id = id;
    }


    /**
     *
     * Saving policy
     *
     * @param parent  the parent.
     * @param time  the time.
     * @param resolution  the resolution.
     */
    public Saving_Policy(ArrayList<Camera> parent, int time, Resolution resolution, int id){

        this.resolution = resolution;
        max_time = time;
        cameras = parent;
        this.id = id;
    }


    /**
     *
     * Add camera
     *
     * @param camera  the camera.
     */
    public void add_camera(Camera camera){

        cameras.add(camera);
    }

    @Override

/**
 *
 * Get display text
 *
 * @return the _display_text
 */
    public String get_display_text() {

        String camera_string = "";
        for(int i = 0; i < cameras.size(); i++){
            camera_string += cameras.get(i).name;
            if(i < cameras.size() - 1)
                camera_string += ", ";
        }


        return camera_string + "\n" + max_time + " minutes" + "\n" + resolution;
    }


    /**
     *
     * To string
     *
     * @return String
     */
    public String toString(){

        return get_display_text();
    }


    /**
     *
     * Get available cameras to add
     *
     * @return the _available_cameras_to_add
     */
    public ArrayList<Camera> get_available_cameras_to_add(){

        ArrayList<Camera> entries = new ArrayList<>();
        ArrayList<Camera> all_cameras = BackEnd.main.get_cameras();
        for(int i = 0; i < all_cameras.size(); i++){
            if(cameras.contains(all_cameras.get(i)))
                continue;
            entries.add(all_cameras.get(i));
        }
        return entries;
    }


    /**
     *
     * Get available resoltions to add
     *
     * @return the _available_resoltions_to_add
     */
    public ArrayList<Resolution> get_available_resoltions_to_add(){

        ArrayList<Resolution> entries = new ArrayList<>();
        ArrayList<Resolution> all_resolutions = Resolution.resolutions;
        for(int i = 0; i < all_resolutions.size(); i++){
            if(resolution.equals(all_resolutions.get(i)))
                continue;
            entries.add(all_resolutions.get(i));
        }
        return entries;
    }


    /**
     *
     * List to string
     *
     * @param savings  the savings.
     * @return ArrayList
     */
    public static ArrayList<String> list_to_string(ArrayList<Saving_Policy> savings){

        ArrayList<String> result = new ArrayList<>();
        for(int i = 0; i < savings.size(); i++)
            result.add(savings.get(i).get_display_text());

        return result;
    }


}
