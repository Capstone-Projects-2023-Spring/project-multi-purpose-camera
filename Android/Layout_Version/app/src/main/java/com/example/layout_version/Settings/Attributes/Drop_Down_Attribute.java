package com.example.layout_version.Settings.Attributes;

import java.util.ArrayList;

public abstract class Drop_Down_Attribute implements Attribute, Drop_Down{
    Object value;
    ArrayList list;
    public Drop_Down_Attribute(String value, ArrayList<String> list){
        this.value = value;
        this.list = list;
    }
    public String type() {
        return "Drop_Down_Attribute";
    }

    public String value(){
        return value.toString();
    }

    public ArrayList get_list(){
        return list;
    }
}