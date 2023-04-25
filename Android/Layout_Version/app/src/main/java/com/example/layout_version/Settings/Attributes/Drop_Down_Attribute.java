package com.example.layout_version.Settings.Attributes;

import java.util.ArrayList;

public abstract class Drop_Down_Attribute implements Attribute, Drop_Down{
    Object value;
    ArrayList list;
    public Drop_Down_Attribute(Object value, ArrayList list){
        this.value = value;
        this.list = list;
    }

    public String value(){
        return value.toString();
    }

    public ArrayList get_list(){
        return list;
    }
}