package com.example.layout_version;

import android.widget.ArrayAdapter;

import java.util.ArrayList;


/**
 * The class Resolution that affects the camera resolution.
 */
public class Resolution {
    int width;
    int height;
    String name;


    /**
     *
     * It is a constructor.
     *
     * @param width  the width.
     * @param height  the height.
     * @param name  the name.
     */
    public Resolution(int width, int height, String name){

        this.width = width;
        this.height = height;
        this.name = name;
    }
    static boolean inited = false;

    /**
     *
     * Initial resolutions
     *
     */
    public static void init_resolutions(){

        inited = true;
        Resolution p480 = new Resolution(720, 480, "480p");
        Resolution p720 = new Resolution(1280, 720, "720p");
        Resolution p1080 = new Resolution(1920, 1080, "1080p");
        Resolution.add_resolution(p480);
        Resolution.add_resolution(p720);
        Resolution.add_resolution(p1080);
    }


    /**
     *
     * Add resolution
     *
     * @param resolution  the resolution.
     */
    public static void add_resolution(Resolution resolution){

        resolutions.add(resolution);
    }

    public static ArrayList<Resolution> resolutions = new ArrayList<>();


    /**
     *
     * Names
     *
     * @param resolutions  the resolutions.
     * @return ArrayList<String>
     */
    public static ArrayList<String> names(ArrayList<Resolution> resolutions){

        ArrayList<String> names = new ArrayList<>();
        for(int i = 0; i < resolutions.size(); i++){
            names.add(resolutions.get(i).name);
        }
        return names;
    }


    /**
     *
     * It is a constructor.
     *
     * @param name  the name.
     */
    public static Resolution name_to_resolution(String name){

        if(!inited)
            init_resolutions();
        Resolution chosen_resolution = null;
        //System.out.println("# resolutions: " + resolutions.size());
        for(int i = 0; i < resolutions.size(); i++){
            if(resolutions.get(i).name.equals(name)){
                chosen_resolution = resolutions.get(i);
                break;
            }else{
                //System.out.println(resolutions.get(i).name +"does not equal " + name);
            }
        }
        if(chosen_resolution == null){
            //System.out.println("resolution: ["+name+"] does not exist");
            throw new RuntimeException();
        }
        return chosen_resolution;
    }


    /**
     *
     * To string
     *
     * @return String
     */
    public String toString(){

        return name;
    }
}
