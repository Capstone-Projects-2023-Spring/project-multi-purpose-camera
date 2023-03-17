package com.example.layout_version;

import java.util.ArrayList;

public class Saving_Policy implements Displayable_Policy{
    public int max_time;
    public ArrayList<Camera> cameras;

    public Saving_Policy(Camera parent, int time){
        max_time = time;
        cameras = new ArrayList<>();
        cameras.add(parent);
    }

    public Saving_Policy(ArrayList<Camera> parent, int time){
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
}
