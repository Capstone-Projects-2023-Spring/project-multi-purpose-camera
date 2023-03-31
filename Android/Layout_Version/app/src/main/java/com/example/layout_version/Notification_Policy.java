package com.example.layout_version;

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
    public Criteria get_criteria(){
        return new Criteria(criteria.type, criteria.magnitude, criteria.duration);
    }
    private Camera camera;

    public Camera get_camera(){
        return camera;
    }

    public Notification_Policy(Criteria criteria, Camera parent, int notification_type){
        this.criteria = criteria;
        this.camera = parent;
        this.type = notification_type;
    }

    @Override
    public String get_display_text() {
        return null;
    }
}
