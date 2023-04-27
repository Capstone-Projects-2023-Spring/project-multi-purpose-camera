package com.example.layout_version.Settings;

import com.example.layout_version.Settings.Attributes.*;
import com.example.layout_version.Settings.Attributes.Number;

import java.util.ArrayList;

public class Notification_Policy implements Displayable_Setting{
    public static final int num_types = 2;
    public static final int[] types = {1001, 1002};
    public static final String[] types_string =  new String[]{"buzz", "silent"};
    public static final int type_buzz = 1001;
    public static final int type_silent = 1002;
    public Notification_Policy this_policy = this;
    private Notification_Policy old;

    public String type_string(int type){
        switch(type){
            case 1001: return "buzz";
            case 1002: return "silent";
        }
        return null;
    }
    public static int type_string_to_int(String text){
        for(int i = 0; i < types_string.length; i++){
            if(text.equals(types_string[i]))
                return types[i];
        }
        return -1;
    }
    //add/notification/type=""/criteria=""/camera=""/id=""/

    //edit/id=""/n

    private int id;
    public int get_id(){
        return id;
    }
    private int type;
    public int get_type(){
        return type;
    }
    public void set_type(int type){
        if(!edited())
            set_edited();
        this.type = type;
    }
    private Criteria criteria;
    public Criteria get_criteria(){
        return new Criteria(criteria.type, criteria.magnitude, criteria.duration, criteria.id);
    }
    public void set_duration(int duration){
        if(!edited())
            set_edited();
        this.criteria.duration = duration;
    }
    public void set_criteria_type(int criteria_type){
        if(!edited())
            set_edited();
        this.criteria.type = criteria_type;
    }
    public void set_criteria_magnitude(int criteria_magnitude){
        if(!edited())
            set_edited();
        this.criteria.magnitude = criteria_magnitude;
    }
    private ArrayList<Camera> cameras;

    public ArrayList<Camera> get_cameras(){
        return cameras;
    }


    public Notification_Policy(Criteria criteria, Camera parent, int notification_type, int id){
        this.criteria = criteria;
        cameras = new ArrayList<>();
        cameras.add(parent);
        this.type = notification_type;
        this.id = id;
    }
    public Notification_Policy(Criteria criteria, ArrayList<Camera> cameras, int notification_type, int id){
        this.criteria = criteria;
        this.cameras = cameras;
        this.type = notification_type;
        this.id = id;
    }

    public String toString() {

        return "criteria: (" + criteria + "), cameras: " + camera_string() + ", type: " + type + ", id: " + id;
    }

    public String camera_string(){
        String camera_string = "";
        for(int i = 0; i < cameras.size(); i++){
            camera_string += cameras.get(i).name;
            if(i < cameras.size() - 1)
                camera_string += ", ";
        }
        return camera_string;
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
    }
    public void remove_camera(Camera camera){
        if(!edited())
            set_edited();
        cameras.remove(camera);
    }

    public boolean is_different(){
        if(!edited())
            return false;
        if(!criteria.equals(old.criteria) || type != old.type || Camera.is_lists_different(cameras, old.cameras))
            return true;
        return false;
    }

    private void set_edited(){
        ArrayList<Camera> new_cameras = new ArrayList<>();
        for(int i = 0; i < cameras.size(); i++){
            new_cameras.add(cameras.get(i));
        }

        old = new Notification_Policy(criteria.copy(), new_cameras, type, id);
    }

    public boolean edited(){
        return old != null;
    }


    @Override
    public Attribute[] get_edit_attributes() {
        ArrayList<Object> resolution_objects = new ArrayList<>();
        for (int i = 0; i < Resolution.resolutions.size(); i++) {
            resolution_objects.add(Resolution.resolutions.get(i));
        }

        Attribute[] attributes = new Attribute[cameras.size() + 5];
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
        attributes[attributes.length - 5] = new Add_Button(BackEnd.main.get_camera_not_in_list(cameras)) {
            @Override
            public void set(Object object) {
                this_policy.add_camera((Camera) object);
            }
        };
        attributes[attributes.length - 4] = new Number(criteria.duration) {
            @Override
            public void set(Object object) {
                this_policy.set_duration((Integer)object);
            }
        };
        attributes[attributes.length - 3] = new Number(criteria.magnitude) {
            @Override
            public void set(Object object) {
                this_policy.set_criteria_magnitude((Integer)object);
            }
        };
        ArrayList<String> not_types_strings = new ArrayList<>();
        for(int i = 0; i < types.length; i++){
            not_types_strings.add(type_string(types[i]));
        }
        attributes[attributes.length - 2] = new Drop_Down_Attribute(type_string(type), not_types_strings) {
            @Override
            public void set(Object object) {
                this_policy.set_type(type_string_to_int(object.toString()));
            }
        };
        ArrayList<String> crit_types_strings = new ArrayList<>();
        for(int i = 0; i < Criteria.types.length; i++){
            crit_types_strings.add(Criteria.type_string(Criteria.types[i]));
        }
        attributes[attributes.length - 1] = new Drop_Down_Attribute(Criteria.type_string(criteria.type), crit_types_strings) {
            @Override
            public void set(Object object){
                this_policy.set_criteria_type(Criteria.type_string_to_int(object.toString()));
            }
        };
        return attributes;

    }

    @Override
    public String get_display_text() {
        return camera_string() + "\n" + criteria.display_text() + "\n " + type_string(type);
    }

    @Override
    public boolean includes_duplicate(Displayable_Setting setting) {
        return false;
    }
}
