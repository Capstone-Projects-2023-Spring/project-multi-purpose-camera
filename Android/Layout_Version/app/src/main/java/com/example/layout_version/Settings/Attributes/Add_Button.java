package com.example.layout_version.Settings.Attributes;

import java.util.ArrayList;

public abstract class Add_Button implements Attribute, Drop_Down{
    ArrayList options;
    public Add_Button(ArrayList options){
        this.options = options;
    }

    public ArrayList get_list(){
        return options;
    }

    public String value() {
        return null;
    }
}