package com.example.blankusing29_2;

import java.util.ArrayList;
import java.util.List;

public class Camera_Manager {
    public List<Camera> cameras = new ArrayList<>();
    public List<Notification> notifications = new ArrayList<>();
    public List<Saving_Policy> saving_policies = new ArrayList<>();
    public Camera_Manager(){
        cameras.add(new Camera());
        cameras.get(0).name = "camera1";
        Saving_Policy policy = new Saving_Policy();
        policy.camera = cameras.get(0);
        policy.max_time = 10;
        saving_policies.add(policy);
    }
}
