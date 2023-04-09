package com.example.layout_version;

import java.util.ArrayList;

/**
 * The class Notification_Policy implements Displayable_Policy. It displays the current notification policy and its layout for the app.
 */
public class Notification_Policy implements Displayable_Policy{
    public static final int type_buzz = 1001;
    public static final int type_silent = 1002;

    //add/notification/type=""/criteria=""/camera=""/id=""/

    //edit/id=""/n

    private int id;
    public int get_id(){
        return id;
    }
    private int type;
    public int get_type(){
        return type;
    }
    private Criteria criteria;

    /**
     * Get criteria
     *
     * @return
     */
    public Criteria get_criteria(){
        return new Criteria(criteria.type, criteria.magnitude, criteria.duration, criteria.id);
    }
    private ArrayList<Camera> cameras;

    /**
     * Get cameras
     *
     * @return
     */
    public ArrayList<Camera> get_cameras(){
        return cameras;
    }

    /**
     * Notification policy along with adding the parent camera
     *
     * @param criteria
     * @param parent
     * @param notification_type
     * @param id
     */
    public Notification_Policy(Criteria criteria, Camera parent, int notification_type, int id){
        this.criteria = criteria;
        cameras = new ArrayList<>();
        cameras.add(parent);
        this.type = notification_type;
        this.id = id;
    }

    /**
     * Notification policy
     *
     * @param criteria
     * @param cameras
     * @param notification_type
     * @param id
     */
    public Notification_Policy(Criteria criteria, ArrayList<Camera> cameras, int notification_type, int id){
        this.criteria = criteria;
        this.cameras = cameras;
        this.type = notification_type;
        this.id = id;
    }

    /**
     * To string
     *
     * @return
     */
    public String toString() {
        String camera_string = "";
        for(int i = 0; i < cameras.size(); i++){
            camera_string += cameras.get(i).name;
            if(i < cameras.size() - 1)
                camera_string += ", ";
        }
        return "criteria: (" + criteria + "), cameras: " + camera_string + ", type: " + type + ", id: " + id;
    }

    /**
     * Get display text
     *
     * @return
     */
    @Override
    public String get_display_text() {
        return null;
    }
}
