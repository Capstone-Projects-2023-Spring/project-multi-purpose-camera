package com.example.layout_version;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class Resolution {
    int width;
    int height;
    String name;

    public Resolution(int width, int height, String name){
        this.width = width;
        this.height = height;
        this.name = name;
    }

    public static void init_resolutions(){
        Resolution p480 = new Resolution(720, 480, "480p");
        Resolution p720 = new Resolution(1280, 720, "720p");
        Resolution p1080 = new Resolution(1920, 1080, "1080p");
        Resolution.add_resolution(p480);
        Resolution.add_resolution(p720);
        Resolution.add_resolution(p1080);
    }

    public static void add_resolution(Resolution resolution){
        resolutions.add(resolution);
    }

    public static ArrayList<Resolution> resolutions = new ArrayList<>();

    public static ArrayList<String> names(ArrayList<Resolution> resolutions){
        ArrayList<String> names = new ArrayList<>();
        for(int i = 0; i < resolutions.size(); i++){
            names.add(resolutions.get(i).name);
        }
        return names;
    }

    public static Resolution name_to_resolution(String name){
        Resolution chosen_resolution = null;
        for(int i = 0; i < resolutions.size(); i++){
            if(resolutions.get(i).name.equals(name)){
                chosen_resolution = resolutions.get(i);
                break;
            }
        }
        return chosen_resolution;
    }

    public String toString(){
        return name;
    }
}
