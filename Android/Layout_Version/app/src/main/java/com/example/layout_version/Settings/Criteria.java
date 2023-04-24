package com.example.layout_version.Settings;

public class Criteria {
    public static final int type_brightness = 1001;
    public static final int type_loudness = 1002;
    public int type;
    public int magnitude;
    public int duration;
    public int id;

    public Criteria(int type, int magnitude, int duration, int id){
        this.type = type;
        this.magnitude = magnitude;
        this.duration = duration;
        this.id = id;
    }

    public String toString(){
        return "type: " + type + ", mag: " + magnitude + ", duration: " + duration + ", id: " + id;
    }
}
