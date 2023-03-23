package com.example.layout_version;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.net.*;
import java.io.*;

public class Receiver_Client implements Runnable{
    static String address = "44.212.17.188";
    static int port = 9999;
    static Socket socket = new Socket(address, port);
    static DataInputStream input = new DataInputStream(socket.getInputStream());
     static DataOutputStream output = new DataOutputStream(socket.getOutputStream());
    public static void main(String args[])throws UnknownHostException, SocketException, IOException {
        //super.onCreate(savedInstanceState);
        //Client client = new Client( ip address, port);
        connect(address, port);
    }
    public static void connect( String address, int port){
        try {
            int BUFF_SIZE = 66536;
            //byte buf[] = null;
            /*
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 1234);
            DatagramSocket datagramSocket = new DatagramSocket(port);
            datagramSocket.send(packet);
            System.out.println(InetAddress.getLocalHost().getHostAddress());
            */

            PrintWriter writer = new PrintWriter(output, true);
            writer.println("Hello from client!");

            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String response = reader.readLine();
            System.out.println("Server response: " + response);

            output.writeUTF("Hello from the other side!");
            output.flush(); // send the message
            output.close(); // close the output stream when we're done.
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