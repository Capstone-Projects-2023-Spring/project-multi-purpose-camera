package com.example.layout_version;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.layout_version.Account.Account;
import com.example.layout_version.Network.NetworkRequestManager;

public class ChannelRegisterActivity extends AppCompatActivity {

    private Button desktopButton;
    private Button phoneButton;
    private EditText deviceNameEdit;

    private ImageView channelRegisterImageView;

    private NetworkRequestManager nrm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_register);

        desktopButton = findViewById(R.id.desktopChannelButton);
        phoneButton = findViewById(R.id.phoneChannelButton);
        deviceNameEdit = findViewById(R.id.channelNameEditText);
        channelRegisterImageView = findViewById(R.id.channelRegisterImageView);
        nrm = new NetworkRequestManager(this);

        desktopButton.setOnClickListener(v -> {
            View view = getLayoutInflater().inflate(R.layout.channel_information, null);
            new AlertDialog.Builder(this)
                    .setView(view)
                    .show();
        });

        if(Account.getInstance().getHardware_id() == null)
        {
            phoneButton.setOnClickListener(v -> {
                channelRegisterImageView.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.baseline_check_circle_outline_192));
            });
        }
        else{
            phoneButton.setBackground(AppCompatResources.getDrawable(this, R.drawable.round_button_diabled));
        }

    }
}