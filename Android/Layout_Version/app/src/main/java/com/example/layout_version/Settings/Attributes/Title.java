package com.example.layout_version.Settings.Attributes;

public abstract class Title implements Attribute{
    String name;
    public Title(String name){
        this.name = name;
    }

    public String value(){
        return name;
    }
}