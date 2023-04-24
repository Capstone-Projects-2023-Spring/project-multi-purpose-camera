package com.example.layout_version.Settings.Attributes;

public abstract class Number implements Attribute{
    int value;
    public Number(int value){
        this.value = value;
    }

    public String value(){
        return String.valueOf(value);
    }
}
