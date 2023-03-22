package com.example.layout_version;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.net.*;
import java.io.*;

public class Receiver_Client implements Runnable {

    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream output = null;

    public void create_connection() {
        //super.onCreate(savedInstanceState);
        //Client client = new Client( ip address, port);
        String address = "44.212.17.188";
        int port = 9999;

        connect(address, port);
    }
    public void connect( String address, int port){
        try {
            socket = new Socket(address, port);
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());

            PrintWriter writer = new PrintWriter(output, true);
            writer.println("Hello from client!");

            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String response = reader.readLine();
            System.out.println("Server response: " + response);
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

    @Override
    public void run() {

    }
}
