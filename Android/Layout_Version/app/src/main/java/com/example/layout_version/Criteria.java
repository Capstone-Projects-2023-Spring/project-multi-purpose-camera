package com.example.layout_version;

/**
 * The class Criteria sets tbe circumstances each camera needs to follow.
 */
public class Criteria {
    public static final int type_brightness = 1001;
    public static final int type_loudness = 1002;
    public int type;
    public int magnitude;
    public int duration;
    public int id;

    /**
     *
     * It is a constructor that takes type, magnitude, duration, and id
     *
     * @param type  the type.
     * @param magnitude  the magnitude.
     * @param duration  the duration.
     * @param id  the id.
     */
    public Criteria(int type, int magnitude, int duration, int id){
        this.type = type;
        this.magnitude = magnitude;
        this.duration = duration;
        this.id = id;
    }

    /**
     *
     * To string in order to display
     *
     * @return String
     */
    public String toString(){
        return "type: " + type + ", mag: " + magnitude + ", duration: " + duration + ", id: " + id;
    }
}
