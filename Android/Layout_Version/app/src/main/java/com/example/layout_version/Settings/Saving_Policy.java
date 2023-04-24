package com.example.layout_version.Settings;


import com.example.layout_version.Settings.Attributes.Attribute;
import com.example.layout_version.Settings.Attributes.Number;
import com.example.layout_version.Settings.Attributes.*;

import java.util.ArrayList;

public class Saving_Policy implements Displayable_Setting{
    public int id;
    private int max_time;
    public int get_max_time(){
        return max_time;
    }
    private ArrayList<Camera> cameras;
    public ArrayList<Camera> get_cameras(){
        ArrayList<Camera> result = new ArrayList<>();
        for(int i = 0; i < cameras.size(); i++){
            result.add(cameras.get(i));
        }
        return result;
    }
    private Resolution resolution;





    public Resolution get_resolution(){
        return resolution;
    }

    public Saving_Policy(Camera parent, int time, Resolution resolution, int id){
        this.resolution = resolution;
        max_time = time;
        cameras = new ArrayList<>();
        cameras.add(parent);
        this.id = id;
    }

    public Saving_Policy(ArrayList<Camera> parent, int time, Resolution resolution, int id){
        this.resolution = resolution;
        max_time = time;
        cameras = parent;
        this.id = id;
    }

    public void add_camera(Camera camera){
        cameras.add(camera);
    }


    public String get_display_text() {
        String camera_string = "";
        for(int i = 0; i < cameras.size(); i++){
            camera_string += cameras.get(i).name;
            if(i < cameras.size() - 1)
                camera_string += ", ";
        }


        return camera_string + "\n" + max_time + " minutes" + "\n" + resolution;
    }

    public String toString(){
        return get_display_text();
    }

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

    public static ArrayList<String> list_to_string(ArrayList<Saving_Policy> savings){
        ArrayList<String> result = new ArrayList<>();
        for(int i = 0; i < savings.size(); i++)
            result.add(savings.get(i).get_display_text());

        return result;
    }


    @Override
    public Attribute[] get_attributes() {
        ArrayList<Object> resolution_objects = new ArrayList<>();
        for (int i = 0; i < Resolution.resolutions.size(); i++) {
            resolution_objects.add(Resolution.resolutions.get(i));
        }

        Attribute[] attributes = new Attribute[cameras.size() + 3];
        for(int i = 0; i < cameras.size(); i++){
            Camera camera = cameras.get(i);
            cameras.remove(camera);
            attributes[i] = new X_Attribute(camera.name) {
                @Override
                public Object set(Object object) {
                    return new Saving_Policy(cameras, max_time, resolution, id);
                }
            };
        }
        attributes[attributes.length - 3] = new Add_Button(BackEnd.main.get_camera_not_in_list(cameras)) {
            @Override
            public Object set(Object object) {
                cameras.add((Camera) object);
                return new Saving_Policy(cameras, max_time, resolution, id);
            }
        };
        attributes[attributes.length - 2] = new Number(max_time) {
            @Override
            public Object set(Object object) {
                return new Saving_Policy(cameras, (Integer) object, resolution, id);
            }
        };
        attributes[attributes.length - 1] = new Drop_Down_Attribute(resolution, resolution_objects) {
            @Override
            public Object set(Object object) {
                return new Saving_Policy(cameras, max_time, (Resolution) object, id);
            }

        };
        return attributes;
    }
}
