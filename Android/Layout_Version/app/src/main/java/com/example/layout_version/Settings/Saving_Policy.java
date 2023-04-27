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
    private Saving_Policy this_policy = this;

    private Saving_Policy old;

    public void set_max_time(int time){
        System.out.println("set_max_time called: " + time);
        if(!edited())
            set_edited();
        max_time = time;
    }

    public void set_resolution(Resolution res){
        if(!edited())
            set_edited();
        resolution = res;
    }
    public void set_cameras(ArrayList<Camera> cameras){
        if(!edited())
            set_edited();
        this.cameras = cameras;
    }

    public void add_camera(Camera camera){
        if(!edited())
            set_edited();
        cameras.add(camera);
    }public void remove_camera(Camera camera){
        if(!edited())
            set_edited();
        cameras.remove(camera);
    }


    public boolean is_different(){
        if(!edited())
            return false;
        if(max_time != old.max_time || resolution != old.resolution || Camera.is_lists_different(cameras, old.cameras))
            return true;
        return false;
    }

    private void set_edited(){
        ArrayList<Camera> new_cameras = new ArrayList<>();
        for(int i = 0; i < cameras.size(); i++){
            new_cameras.add(cameras.get(i));
        }
        old = new Saving_Policy(new_cameras, max_time, resolution, id);
    }

    public boolean edited(){
        return old != null;
    }

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

    public String get_display_text() {
        String camera_string = "";
        for(int i = 0; i < cameras.size(); i++){
            camera_string += cameras.get(i).name;
            if(i < cameras.size() - 1)
                camera_string += ", ";
        }


        return camera_string + "\n" + max_time + " minutes" + "\n" + resolution;
    }

    public boolean includes_duplicate(Displayable_Setting setting){
        Saving_Policy temp = (Saving_Policy) setting;
        if(temp.get_resolution() != resolution)
            return false;
        ArrayList<Camera> temp_cameras = temp.get_cameras();
        for(int camera_index = 0; camera_index < cameras.size(); camera_index++){
            Camera camera = cameras.get(camera_index);
            if(temp_cameras.contains(camera))
                return true;
        }
        return false;
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
    public Attribute[] get_edit_attributes() {
        ArrayList<String> resolution_objects = new ArrayList<>();
        for (int i = 0; i < Resolution.resolutions.size(); i++) {
            resolution_objects.add(Resolution.resolutions.get(i).name);
        }

        Attribute[] attributes = new Attribute[cameras.size() + 3];
        for(int i = 0; i < cameras.size(); i++){
            Camera camera = cameras.get(i);
            ArrayList<Camera> cameras_if_delete = new ArrayList<>();
            for(int j = 0; j < cameras.size(); j++){
                cameras_if_delete.add(cameras.get(j));
            }
            cameras_if_delete.remove(camera);
            attributes[i] = new X_Attribute(camera.name) {
                @Override
                public void set(Object object) {
                    this_policy.remove_camera(camera);
                }
            };
        }
        attributes[attributes.length - 3] = new Add_Button(BackEnd.main.get_camera_not_in_list(cameras)) {
            @Override
            public void set(Object object) {
                this_policy.add_camera((Camera) object);
            }
        };
        attributes[attributes.length - 2] = new Number(max_time) {
            @Override
            public void set(Object object) {
                this_policy.set_max_time((Integer)object);
            }
        };
        attributes[attributes.length - 1] = new Drop_Down_Attribute(resolution.name, resolution_objects) {
            @Override
            public void set(Object object) {
                Resolution res = Resolution.name_to_resolution(object.toString());
                this_policy.set_resolution(res);
            }
        };
        return attributes;
    }
}