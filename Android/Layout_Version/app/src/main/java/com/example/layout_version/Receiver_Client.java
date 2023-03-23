package com.example.layout_version;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class Receiver_Client implements Runnable{
    static String address = "44.212.17.188";
    static int port = 9999;
    static String ID = "ABCDEFGH";
    //static Socket socket = new Socket(address, port);
    //static DataInputStream input = new DataInputStream(socket.getInputStream());
     //static DataOutputStream output = new DataOutputStream(socket.getOutputStream());
    public Receiver_Client(){
        //super.onCreate(savedInstanceState);
        //Client client = new Client( ip address, port);
        System.out.println("Start of program");
        connect(address, port);
    }
    public static void connect( String address, int port) {
        try {
            System.out.println("Start of connect method");
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName(address);
            System.out.println("checkpoint 1");
            String sentence = "R" + ID;
            byte[] bytes = sentence.getBytes(StandardCharsets.UTF_8);
            byte[] receivebytes = new byte[5];
            DatagramPacket sendPacket = new DatagramPacket(bytes, bytes.length, IPAddress, port);
            System.out.println("checkpoint 2");
            clientSocket.send(sendPacket);
            System.out.println("checkpoint 3");
            DatagramPacket receivePacket = new DatagramPacket(receivebytes, receivebytes.length);
            System.out.println("checkpoint 4");
            clientSocket.receive(receivePacket);
            System.out.println("checkpoint 5");
            receivebytes = receivePacket.getData();
            System.out.println("checkpoint 6");
            String decoded = new String(receivebytes, "UTF-8");
            System.out.println("From server: " + decoded);
            clientSocket.close();
        } catch (UnknownHostException u) {
            System.out.println(u);
        } catch (IOException i) {
            System.out.println(i);
        }
    }
        String line = "";
/*
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
    }*/

    @Override
    public void run() {
    }
}