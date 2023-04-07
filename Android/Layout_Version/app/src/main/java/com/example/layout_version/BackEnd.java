package com.example.layout_version;

import java.util.ArrayList;

public class BackEnd {
    public static BackEnd main;

    public BackEnd(ArrayList<Camera> cameras, ArrayList<Saving_Policy> savings, ArrayList<Notification_Policy> notifications){
        this.cameras = cameras;
        this.savings = savings;
        this.notifications = notifications;
    }

    public static void get_backend_from_database(){
        main = Database_Manager.create_BackEnd();
    }

    private ArrayList<Camera> cameras = new ArrayList<>();
    public ArrayList<Camera> get_cameras(){
        ArrayList<Camera> result = new ArrayList<>();
        for(int i = 0; i < cameras.size(); i++)
            result.add(cameras.get(i));
        return result;
    }


    private ArrayList<Saving_Policy> savings = new ArrayList<>();
    public ArrayList<Saving_Policy> get_savings(){
        ArrayList<Saving_Policy> result = new ArrayList<>();
        for(int i = 0; i < savings.size(); i++)
            result.add(savings.get(i));
        return result;
    }
    private ArrayList<Notification_Policy> notifications = new ArrayList<>();
    public ArrayList<Notification_Policy> get_notifications(){
        ArrayList<Notification_Policy> result = new ArrayList<>();
        for(int i = 0; i < notifications.size(); i++)
            result.add(notifications.get(i));
        return result;
    }
    public static void init(){
        main = Database_Manager.create_BackEnd();
    }
    public static void init_test_objects(){
        ArrayList<Saving_Policy> savings = new ArrayList<>();
        ArrayList<Notification_Policy> notifications = new ArrayList<>();
        ArrayList<Camera> cameras = new ArrayList<>();

        Resolution.init_resolutions();

        cameras.add(new Camera("camera 1", Resolution.name_to_resolution("1080p"), 1, 1));
        cameras.add(new Camera("camera 2", Resolution.name_to_resolution("1080p"), 2, 1));
        cameras.add(new Camera("camera 3", Resolution.name_to_resolution("1080p"), 3, 1));
        cameras.add(new Camera("camera 4", Resolution.name_to_resolution("1080p"), 4, 1));
        Criteria criteria = new Criteria(Criteria.type_brightness, 10, 10, 1);
        notifications.add(new Notification_Policy(criteria, cameras.get(0), Notification_Policy.type_buzz, 1));
        savings.add(new Saving_Policy(cameras.get(0), 10, Resolution.name_to_resolution("1080p"), 1));
        Saving_Policy saving = new Saving_Policy(cameras.get(3), 30, Resolution.name_to_resolution("1080p"), 2);
        saving.add_camera(cameras.get(2));
        savings.add(saving);
        savings.add(new Saving_Policy(cameras.get(1), 20, Resolution.name_to_resolution("1080p"), 3));
        savings.add(savings.get(savings.size() - 1));
        savings.add(savings.get(savings.size() - 1));

        main = new BackEnd(cameras, savings, notifications);
    }


    public Camera name_to_camera(String name){
        Camera chosen_camera = null;
        for(int i = 0; i < cameras.size(); i++){
            if(cameras.get(i).name.equals(name)){
                chosen_camera = cameras.get(i);
                break;
            }
        }
        return chosen_camera;
    }


    public BackEnd get_copy_data(){
        BackEnd copy = new BackEnd(get_cameras(), get_savings(),get_notifications());
        return copy;
    }

    public boolean saving_lists_same(ArrayList<Saving_Policy> list){
        if(savings.size() != list.size())
            return false;
        for(int i = 0; i < savings.size(); i++){
            if(!savings.get(i).equals(list.get(i)))
                return false;
        }
        return true;
    }

    public boolean notification_lists_same(ArrayList<Notification_Policy> list){
        if(notifications.size() != list.size())
            return false;
        for(int i = 0; i < savings.size(); i++){
            if(!savings.get(i).equals(list.get(i)))
                return false;
        }
        return true;
    }

    public void get_different_saving(ArrayList<Saving_Policy> list, ArrayList<Saving_Policy> to_delete, ArrayList<Saving_Policy> to_add){


        ArrayList<Saving_Policy> old_temp = to_delete;
        ArrayList<Saving_Policy> new_temp = to_add;

        for(int i = 0; i < savings.size(); i++){
            old_temp.add(savings.get(i));
        }
        for(int i = 0; i < list.size(); i++){
            new_temp.add(list.get(i));
        }




        for(int i = new_temp.size() - 1; i >= 0; i--){
            Saving_Policy policy = new_temp.get(i);
            if(old_temp.contains(policy)){
                old_temp.remove(policy);
                new_temp.remove(policy);
            }
        }
    }
}

