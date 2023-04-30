package com.example.layout_version;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class ChannelRegisterActivity extends AppCompatActivity {

    private Button desktopButton;
    private Button phoneButton;
    private EditText deviceNameEdit;

    private ImageView channelRegisterImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_register);

        desktopButton = findViewById(R.id.desktopChannelButton);
        phoneButton = findViewById(R.id.phoneChannelButton);
        deviceNameEdit = findViewById(R.id.channelNameEditText);
        channelRegisterImageView = findViewById(R.id.channelRegisterImageView);

        desktopButton.setOnClickListener(v -> {
            View view = getLayoutInflater().inflate(R.layout.channel_information, null);
            new AlertDialog.Builder(this)
                    .setView(view)
                    .show();
        });

        phoneButton.setOnClickListener(v -> {
            channelRegisterImageView.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.baseline_check_circle_outline_192));
        });
    }
}