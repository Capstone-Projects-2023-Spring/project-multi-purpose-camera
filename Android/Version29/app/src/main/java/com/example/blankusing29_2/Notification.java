package com.example.blankusing29_2;

public class Notification implements Displayable_Policy{
    public int id;
    public int type;
    public Criteria criteria;
    public Camera camera;

    @Override
    public String get_display_text() {
        return null;
    }
}
