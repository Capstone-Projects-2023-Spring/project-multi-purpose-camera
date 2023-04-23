package com.example.layout_version;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.layout_version.Account.Account_Page;

public class Bluetooth_Page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bluetooth_hardware_page);

        Button serialbtn = findViewById(R.id.serialButton);

        serialbtn.setOnClickListener(view -> {
            startActivity(new Intent(Bluetooth_Page.this, MainActivity.class));
        });
    }
}
