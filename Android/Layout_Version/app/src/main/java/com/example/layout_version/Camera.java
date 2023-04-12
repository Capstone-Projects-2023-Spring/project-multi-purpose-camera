package com.example.layout_version;

import java.util.ArrayList;

public class Camera {
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
