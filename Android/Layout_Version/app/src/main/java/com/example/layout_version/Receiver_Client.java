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
    public void main(){
        //super.onCreate(savedInstanceState);
        //Client client = new Client( ip address, port);
        connect(address, port);
    }
    public static void connect( String address, int port) {
        try {
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName(address);
            String sentence = "R" + ID;
            byte[] bytes = sentence.getBytes(StandardCharsets.UTF_8);
            byte[] receivebytes = sentence.getBytes(StandardCharsets.UTF_8);
            DatagramPacket sendPacket = new DatagramPacket(bytes, bytes.length, IPAddress, port);
            clientSocket.send(sendPacket);
            DatagramPacket receivePacket = new DatagramPacket(receivebytes, receivebytes.length);
            clientSocket.receive(receivePacket);
            receivebytes = receivePacket.getData();
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