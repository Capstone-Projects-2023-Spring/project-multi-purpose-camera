package com.example.layout_version;

import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class Receiver_Client{

    public static void custom_run(){
        System.out.println("Start of program");
        if (! Python.isStarted()) {
            Python.start(new AndroidPlatform(context));
        }
    }
}

