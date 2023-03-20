package com.example.layout_version;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.net.*;
import java.io.*;

public abstract class Receiver_Client extends AppCompatActivity implements Runnable {

    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream output = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Client client = new Client( ip address, port);
    }
    public void connect( String address, int port ){
        try {
            socket = new Socket(address, port);
            input = new DataInputStream(System.in);
            output = new DataOutputStream(socket.getOutputStream());
        }
        catch(UnknownHostException u){
            System.out.println(u);
        }
        catch(IOException i){
            System.out.println(i);
        }
        String line = "";

        while(!line.equals("Over")){
            try{
                line = input.readLine();
                output.writeUTF(line);
            }
            catch(IOException i){
                System.out.println(i);
            }
        }
        try{
            input.close();
            output.close();
            socket.close();
        }
        catch(IOException i){
            System.out.println(i);
        }
    }
}
