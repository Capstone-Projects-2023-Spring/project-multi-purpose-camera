package com.example.layout_version.Settings.Attributes;

public abstract class Regular implements Attribute{
    String value;
    public Regular(String value){
        this.value = value;
    }

    public String value(){
        return value;
    }
    public String type() {
        return "Regular";
    }
}