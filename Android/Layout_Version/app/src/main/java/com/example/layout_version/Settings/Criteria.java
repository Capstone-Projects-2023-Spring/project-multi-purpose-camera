package com.example.layout_version.Settings;

public class Criteria {
    public static final String[] types_string = {"brightness", "loudness"};
    public static final int[] types = {1001, 1002};
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

    public static int type_string_to_int(String text){
        for(int i = 0; i < types_string.length; i++){
            if(text.equals(types_string[i]))
                return types[i];
        }
        return -1;
    }

    public static String type_string(int type_int){
        for(int i = 0; i < types.length; i++){
            if(type_int == types[i])
                return types_string[i];
        }
        return null;
    }

    public Criteria copy() {
        return new Criteria(type, magnitude, duration, id);
    }

    public String toString(){
        return "type: " + type_string(type) + ", mag: " + magnitude + ", duration: " + duration + ", id: " + id;
    }

    public String display_text(){
        return "type: " + type_string(type) + ", mag: " + magnitude + ", duration: " + duration;
    }
}
