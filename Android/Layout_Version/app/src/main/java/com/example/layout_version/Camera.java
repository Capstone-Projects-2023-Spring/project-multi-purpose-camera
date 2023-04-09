package com.example.layout_version;

import java.util.ArrayList;

/**
 * The class Camera creates the object for Camera
 */
public class Camera {

    public int id;
    public String name;
    public Resolution resolution;
    public int account_id;

    /**
     * Sets the name, resolution, id, and account_id to this class's name, resolution, id, and account_id
     *
     * @param name
     * @param resolution
     * @param id
     * @param account_id
     */
    public Camera(String name, Resolution resolution, int id, int account_id){
        this.resolution = resolution;
        this.name = name;
        this.id = id;
        this.account_id = account_id;
    }

    /**
     * Gets the names for the cameras and displays it.
     *
     * @param cameras
     * @return
     */
    public static ArrayList<String> names (ArrayList<Camera> cameras) {
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < cameras.size(); i++) {
            result.add(cameras.get(i).name);
        }
        return result;
    }

    /**
     * ToString in order to display
     *
     * @return
     */
    public String toString(){
        return name;
    }
}
