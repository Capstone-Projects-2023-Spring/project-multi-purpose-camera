package com.example.layout_version;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Array;
import java.util.Arrays;
import java.util.Base64;

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
            DatagramSocket videoSocket = new DatagramSocket();
            DatagramSocket audioSocket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName(address);
            String sentence = "R" + ID;
            byte[] bytes = sentence.getBytes(StandardCharsets.UTF_8);
            byte[] receivebytes = new byte[5];
            DatagramPacket sendPacket = new DatagramPacket(bytes, bytes.length, IPAddress, port);
            clientSocket.send(sendPacket);
            DatagramPacket receivePacket = new DatagramPacket(receivebytes, receivebytes.length);
            clientSocket.receive(receivePacket);
            receivebytes = receivePacket.getData();
            String decoded = new String(receivebytes, "UTF-8");
            System.out.println("From server: " + decoded);
            int port_num = Integer.parseInt(decoded);
            clientSocket.close();
            byte[] videoInitMessage = "Vid_Init".getBytes();
            byte[] audioInitMessage = "Aud_Init".getBytes();
            DatagramPacket startVideoPacket = new DatagramPacket(videoInitMessage, videoInitMessage.length, IPAddress, port_num);
            DatagramPacket startAudioPacket = new DatagramPacket(audioInitMessage, audioInitMessage.length, IPAddress, port_num + 1);
            videoSocket.send(startVideoPacket);
            // audioSocket.send(startAudioPacket);
            while(true){
                byte[] receivevideobytes = new byte[65536];
                //byte[] receiveaudiobytes = new byte[8];
                DatagramPacket receiveVideoPacket = new DatagramPacket(receivevideobytes, receivevideobytes.length);
                //DatagramPacket receiveAudioPacket = new DatagramPacket(receiveaudiobytes, receiveaudiobytes.length);
                videoSocket.receive(receiveVideoPacket);
                System.out.println(receiveVideoPacket);
                byte[] byte_arr = trim(receivevideobytes);
                //receivevideobytes = receiveAudioPacket.getData();

                System.out.println("Size of video packet: " + byte_arr.length);
                //byte[] v_result = trim(receivevideobytes);
                //byte[] a_result = trim(receivevideobytes);
                //audioSocket.receive(receiveAudioPacket);
                //receiveaudiobytes = receiveAudioPacket.getData();
                // System.out.println("Size of video packet: " + a_result.length);

            }
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
    public static byte[] trim(byte[] data) {
        byte[] input = data;
        int i = input.length;
        while (i-- > 0 && input[i] == 0) {}

        byte[] output = new byte[i+1];
        System.arraycopy(input, 0, output, 0, i+1);
        return output;
    }

    @Override
    public void run() {
    }
}