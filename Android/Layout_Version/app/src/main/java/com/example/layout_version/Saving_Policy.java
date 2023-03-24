package com.example.layout_version;

import java.util.ArrayList;

public class Saving_Policy implements Displayable_Policy{
    public int max_time;
    public ArrayList<Camera> cameras;
    public Resolution resolution;

    public Saving_Policy(Camera parent, int time, Resolution resolution){
        this.resolution = resolution;
        max_time = time;
        cameras = new ArrayList<>();
        cameras.add(parent);
    }

    public Saving_Policy(ArrayList<Camera> parent, int time, Resolution resolution){
        this.resolution = resolution;
        max_time = time;
        cameras = parent;
    }

    public void add_camera(Camera camera){
        cameras.add(camera);
    }

    @Override
    public String get_display_text() {
        String camera_string = "";
        for(int i = 0; i < cameras.size(); i++){
            camera_string += cameras.get(i).name;
            if(i < cameras.size() - 1)
                camera_string += ", ";
        }


        return camera_string + "\n" + max_time + " minutes";
    }

    public ArrayList<Camera> get_available_cameras_to_add(){
        ArrayList<Camera> entries = new ArrayList<>();
        ArrayList<Camera> all_cameras = BackEnd.cameras;
        for(int i = 0; i < all_cameras.size(); i++){
            if(cameras.contains(all_cameras.get(i)))
                continue;
            entries.add(all_cameras.get(i));
        }
        return entries;
    }

    public ArrayList<Resolution> get_available_resoltions_to_add(){
        ArrayList<Resolution> entries = new ArrayList<>();
        ArrayList<Resolution> all_resolutions = BackEnd.resolutions;
        for(int i = 0; i < all_resolutions.size(); i++){
            if(resolution.equals(all_resolutions.get(i)))
                continue;
            entries.add(all_resolutions.get(i));
        }
        return entries;
    }
}
