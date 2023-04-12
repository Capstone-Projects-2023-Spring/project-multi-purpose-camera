package com.example.blankusing29_2;

public class Saving_Policy implements Displayable_Policy{
    public int max_time;
    public Camera camera;

    @Override
    public String get_display_text() {
        return camera.name + ": " + max_time + " minutes";
    }
}
