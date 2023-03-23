package com.example.layout_version;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.net.*;
import java.io.*;

public class Receiver_Client implements Runnable{
    static String address = "44.212.17.188";
    static int port = 9999;
    //static Socket socket = new Socket(address, port);
    //static DataInputStream input = new DataInputStream(socket.getInputStream());
     //static DataOutputStream output = new DataOutputStream(socket.getOutputStream());
    public static void main( String args[] )throws UnknownHostException, SocketException, IOException {
        //super.onCreate(savedInstanceState);
        //Client client = new Client( ip address, port);
        connect(address, port);
    }
    public static void connect( String address, int port) {
        try {
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName("localhost");
            byte[] sendData = new byte[1024];
            byte[] receiveData = new byte[1024];
            String sentence = "Hello, server!";
            sendData = sentence.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            clientSocket.send(sendPacket);
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);
            String modifiedSentence = new String(receivePacket.getData());
            System.out.println("From server: " + modifiedSentence);
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