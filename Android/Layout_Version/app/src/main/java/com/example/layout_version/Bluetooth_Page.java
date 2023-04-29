package com.example.layout_version;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import com.example.layout_version.Account.Account_Page;
import com.example.layout_version.Bluetooth.BluetoothManager;

public class Bluetooth_Page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Notifications notif = new Notifications(this);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        notif.send_Bluetooth_Notification(managerCompat);
        setContentView(R.layout.bluetooth_hardware_page);

        Button serialbtn = findViewById(R.id.serialButton);

        serialbtn.setOnClickListener(view -> {
            BluetoothManager bluetoothManager = new BluetoothManager();
            bluetoothManager.show_bluetooth_devices(this);
        });
    }
}
