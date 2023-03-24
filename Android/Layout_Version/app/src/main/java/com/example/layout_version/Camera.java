package com.example.layout_version;

import java.util.ArrayList;

public class Camera {
    public int id;
    public String name;
    public Resolution resolution;
    public Camera(String name, Resolution resolution){
        this.resolution = resolution;
        this.name = name;
    }

    public static ArrayList<String> names (ArrayList<Camera> cameras){
        ArrayList<String> result = new ArrayList<>();
        for(int i = 0; i < cameras.size(); i++){
            result.add(cameras.get(i).name);
        }
        return result;
    }
}
