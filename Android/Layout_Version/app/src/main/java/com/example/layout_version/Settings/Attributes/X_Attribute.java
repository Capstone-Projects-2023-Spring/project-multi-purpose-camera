package com.example.layout_version.Settings.Attributes;

public abstract class X_Attribute implements Attribute{
    String value;
    public X_Attribute(String value){
        this.value = value;
    }

    public String value(){
        return value;
    }


}