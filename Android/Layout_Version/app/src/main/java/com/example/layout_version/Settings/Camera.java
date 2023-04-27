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

    public Attribute[] get_edit_attributes(){
        Attribute[] attributes = new Attribute[]{
                new Title(name) {
                    @Override
                    public void set(Object object) {

                    }
                }
        };
        return attributes;
    }

    @Override
    public String get_display_text() {
        return null;
    }

    @Override
    public boolean includes_duplicate(Displayable_Setting setting) {
        return false;
    }

    public static ArrayList<String> names (ArrayList<Camera> cameras) {
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < cameras.size(); i++) {
            result.add(cameras.get(i).name);
        }
        return result;
    }

    public static boolean is_lists_different(ArrayList<Camera> one, ArrayList<Camera> two){
        ArrayList<Camera> temp_one = new ArrayList<>();
        ArrayList<Camera> temp_two = new ArrayList<>();
        for(int i = 0; i < one.size(); i++)
            temp_one.add(one.get(i));
        for(int i = 0; i < two.size(); i++)
            temp_two.add(two.get(i));

        for(int i = 0; i < temp_one.size(); i++)
            if(temp_two.contains(temp_one.get(i)))
                temp_two.remove(temp_one.get(i));
        for(int i = 0; i < temp_two.size(); i++)
            if(temp_one.contains(temp_two.get(i)))
                temp_one.remove(temp_two.get(i));
        if(temp_one.size() > 0 || temp_two.size() > 0)
            return true;
        return false;
    }



    public String toString(){
        return name;
    }
}