package com.example.layout_version.Settings;

import com.example.layout_version.Settings.Attributes.*;

import java.util.ArrayList;

public class Notification_Policy implements Displayable_Setting{
    public static final int type_buzz = 1001;
    public static final int type_silent = 1002;

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
    private Criteria criteria;
    public Criteria get_criteria(){
        return new Criteria(criteria.type, criteria.magnitude, criteria.duration, criteria.id);
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
        String camera_string = "";
        for(int i = 0; i < cameras.size(); i++){
            camera_string += cameras.get(i).name;
            if(i < cameras.size() - 1)
                camera_string += ", ";
        }
        return "criteria: (" + criteria + "), cameras: " + camera_string + ", type: " + type + ", id: " + id;
    }


    @Override
    public Attribute[] get_attributes() {
        return new Attribute[0];
    }
}
