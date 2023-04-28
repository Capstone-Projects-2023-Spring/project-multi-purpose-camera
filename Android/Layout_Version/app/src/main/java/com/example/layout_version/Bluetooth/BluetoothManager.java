package com.example.layout_version.Bluetooth;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class BluetoothManager {

    private static final String TAG = "BluetoothConnection";
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); // Standard SerialPortService ID
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothSocket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    public BluetoothManager() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    @SuppressLint("MissingPermission")
    public boolean connectToDevice(String deviceAddress) {
        BluetoothDevice device = bluetoothAdapter.getRemoteDevice(deviceAddress);
        try {
            socket = device.createRfcommSocketToServiceRecord(MY_UUID);
            socket.connect();
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            read();
            write("Change name\nnew_device");
            return true;
        } catch (IOException e) {
            Log.e(TAG, "Error connecting to device", e);
            return false;
        }
    }

    public void write(String message) {
        try {
            outputStream.write(message.getBytes());
        } catch (IOException e) {
            Log.e(TAG, "Error sending data", e);
        }
    }

    public String read() {
        byte[] buffer = new byte[1024];
        int bytes;
        try {
            bytes = inputStream.read(buffer);
            String result = new String(buffer, 0, bytes);
            System.out.println(result);
            return result;
        } catch (IOException e) {
            Log.e(TAG, "Error reading data", e);
            return null;
        }

    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            Log.e(TAG, "Error closing socket", e);
        }
    }

    @SuppressLint("MissingPermission")
    public void show_bluetooth_devices(Context context){
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

        if (pairedDevices.isEmpty()) {
            Toast.makeText(context, "No paired devices found", Toast.LENGTH_LONG).show();
        } else {
            showDeviceListDialog(pairedDevices, context);
            // Do something with the paired devices, e.g., show the device list dialog
        }

    }

    @SuppressLint("MissingPermission")
    private void showDeviceListDialog(Set<BluetoothDevice> pairedDevices, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Select Raspberry Pi");

        List<BluetoothDevice> deviceList = new ArrayList<>(pairedDevices);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1);

        for (BluetoothDevice device : deviceList) {
            adapter.add(device.getName() + "\n" + device.getAddress());
        }

        builder.setAdapter(adapter, (dialog, which) -> {
            BluetoothDevice selectedDevice = deviceList.get(which);
            if (this.connectToDevice(selectedDevice.getAddress())) {
                System.out.println("selected device: "+selectedDevice.getAddress());
                Toast.makeText(context, "Connected to " + selectedDevice.getName(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Failed to connect to " + selectedDevice.getName(), Toast.LENGTH_LONG).show();
                return;
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }
}