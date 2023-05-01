package com.example.layout_version;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.layout_version.Account.Account;
import com.example.layout_version.Network.NetworkRequestManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class ChannelRegisterActivity extends AppCompatActivity {

    private Button desktopButton;
    private Button phoneButton;
    private EditText deviceNameEdit;

    private ImageView channelRegisterImageView;
    private TextView errorTextView;
    private ProgressBar progressBar;

    private NetworkRequestManager nrm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_register);

        desktopButton = findViewById(R.id.desktopChannelButton);
        phoneButton = findViewById(R.id.phoneChannelButton);
        deviceNameEdit = findViewById(R.id.channelNameEditText);
        channelRegisterImageView = findViewById(R.id.channelRegisterImageView);
        errorTextView = findViewById(R.id.errorTextView);
        progressBar = findViewById(R.id.progressBar);

        nrm = new NetworkRequestManager(this, progressBar);

        errorTextView.setText("");

        desktopButton.setOnClickListener(v -> {
            String name = deviceNameEdit.getText().toString();
            if(name.equals(""))
            {
                errorTextView.setText("Give device name");
                return;
            }

            JSONObject jsonObject = new JSONObject(
                    Map.of(
                        "token", Account.getInstance().getTokenData().getValue(),
                            "device_name", name
                    ));
            desktopButton.setEnabled(false);
            nrm.Put(getResources().getString(R.string.device_register_url), jsonObject,
                    json -> {
                        View view = getLayoutInflater().inflate(R.layout.channel_information, null);
                        TextView ingestEndpoint = view.findViewById(R.id.ingestEndpointTextView);
                        TextView streamKey = view.findViewById(R.id.streamKeyTextView);
                        try{
                            JSONObject hardware = json.getJSONObject("hardware");
                            ingestEndpoint.setText(hardware.getString("ingest_endpoint"));
                            streamKey.setText(hardware.getString("stream_key"));
                            errorTextView.setText("");
                        }catch(JSONException e)
                        {
                            errorTextView.setText("Internal error when creating dialog");
                        }

                        new AlertDialog.Builder(this)
                                .setView(view)
                                .show();
                        desktopButton.setEnabled(true);
                    },
                    json -> {
                        errorTextView.setText("Failed to connect to process request. Try again");
                        desktopButton.setEnabled(true);
                    });

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