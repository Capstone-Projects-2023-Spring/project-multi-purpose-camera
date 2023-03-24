package com.example.layout_version;

import java.util.ArrayList;

public class BackEnd {
    public static ArrayList<Camera> cameras = new ArrayList<>();

    public static ArrayList<Resolution> resolutions = new ArrayList<>();
    public static ArrayList<Saving_Policy> savings = new ArrayList<>();
    public static ArrayList<Notification_Policy> notifications = new ArrayList<>();

    public static void init_test_objects(){
        Resolution p480 = new Resolution(720, 480, "480p");
        Resolution p720 = new Resolution(1280, 720, "720p");
        Resolution p1080 = new Resolution(1920, 1080, "1080p");
        resolutions.add(p480);
        resolutions.add(p720);
        resolutions.add(p1080);

        cameras.add(new Camera("camera 1", p1080));
        cameras.add(new Camera("camera 2", p1080));
        cameras.add(new Camera("camera 3", p1080));
        cameras.add(new Camera("camera 4", p1080));
        Criteria criteria = new Criteria(Criteria.type_brightness, 10, 10);
        notifications.add(new Notification_Policy(criteria, cameras.get(0), Notification_Policy.type_buzz));
        savings.add(new Saving_Policy(cameras.get(0), 10, p1080));
        Saving_Policy saving = new Saving_Policy(cameras.get(3), 30, p1080);
        saving.add_camera(cameras.get(2));
        savings.add(saving);
        savings.add(new Saving_Policy(cameras.get(1), 20, p1080));
        savings.add(savings.get(savings.size() - 1));
        savings.add(savings.get(savings.size() - 1));
    }

    public static ArrayList<String>  get_policy_string_saving(){
        ArrayList<String> result = new ArrayList<>();
        for(int i = 0; i < savings.size(); i++)
            result.add(savings.get(i).get_display_text());

        return result;
    }

    public static Camera name_to_camera(String name){
        Camera chosen_camera = null;
        for(int i = 0; i < BackEnd.cameras.size(); i++){
            if(BackEnd.cameras.get(i).name.equals(name)){
                chosen_camera = BackEnd.cameras.get(i);
                break;
            }
        }
        return chosen_camera;
    }

    public static Resolution name_to_resolution(String name){
        Resolution chosen_resolution = null;
        for(int i = 0; i < BackEnd.resolutions.size(); i++){
            if(BackEnd.resolutions.get(i).name.equals(name)){
                chosen_resolution = BackEnd.resolutions.get(i);
                break;
            }
        }
        return chosen_resolution;
    }
}

