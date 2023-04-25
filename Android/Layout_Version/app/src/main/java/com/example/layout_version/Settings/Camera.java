package com.example.layout_version.Settings;

import com.example.layout_version.Settings.Attributes.*;

import java.util.ArrayList;

public class Camera implements Displayable_Setting{
    public int id;
    public String name;
    public Resolution resolution;
    public int account_id;
    public Camera(String name, Resolution resolution, int id, int account_id){
        this.resolution = resolution;
        this.name = name;
        this.id = id;
        this.account_id = account_id;
    }

    public int get_num_items(){
        return 2;
    }

    public Attribute[] get_attributes(){
        Attribute[] attributes = new Attribute[]{
                new Title(name) {
                    @Override
                    public Object set(Object object) {
                        return new Camera((String)object, resolution, id, account_id);
                    }
                }
        };
        return attributes;
    }

    public static ArrayList<String> names (ArrayList<Camera> cameras) {
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < cameras.size(); i++) {
            result.add(cameras.get(i).name);
        }
        return result;
    }



    public String toString(){
        return name;
    }
}