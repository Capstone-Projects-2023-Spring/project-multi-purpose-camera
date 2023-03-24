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

    public static ArrayList<String> names(ArrayList<Resolution> resolutions){
        ArrayList<String> names = new ArrayList<>();
        for(int i = 0; i < resolutions.size(); i++){
            names.add(resolutions.get(i).name);
        }
        return names;
    }
}
