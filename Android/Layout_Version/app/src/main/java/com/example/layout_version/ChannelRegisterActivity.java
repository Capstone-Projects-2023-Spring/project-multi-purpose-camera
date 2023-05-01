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
import com.example.layout_version.Network.NetworkInterface;
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
            registerDevice(desktopButton,
                    json -> {
                        View view = getLayoutInflater().inflate(R.layout.channel_information, null);
                        TextView ingestEndpoint = view.findViewById(R.id.ingestEndpointTextView);
                        TextView streamKey = view.findViewById(R.id.streamKeyTextView);
                        Button emailButton = view.findViewById(R.id.emailButton);
                        ProgressBar progressBar = view.findViewById(R.id.progressBar);
                        ImageView networkResultView = view.findViewById(R.id.networkResultView);
                        try{
                            JSONObject hardware = json.getJSONObject("hardware");
                            ingestEndpoint.setText(hardware.getString("ingest_endpoint"));
                            streamKey.setText(hardware.getString("stream_key"));
                            String deviceId = (hardware.getString("device_id"));
                            NetworkRequestManager nrm = new NetworkRequestManager(this, progressBar, networkResultView);
                            emailButton.setOnClickListener(view1 -> {
                                email(emailButton, nrm, deviceId,
                                        json1 -> {

                                        },
                                        json1 -> {

                                        });
                            });
                            errorTextView.setText("");
                        }catch(JSONException e)
                        {
                            errorTextView.setText("Internal error when creating dialog");
                        }
                        new AlertDialog.Builder(this)
                                .setView(view)
                                .show();
                    },
                    json -> {});
        });

        if(!isNumeric(Account.getInstance().getHardware_id()))
        {
            phoneButton.setOnClickListener(v -> {
                registerDevice(phoneButton,
                        json -> {
                            String deviceId;
                            String hardwareId;
                            try{
                                deviceId = json.getJSONObject("hardware").getString("device_id");
                                hardwareId = json.getJSONObject("hardware").getString("hardware_id");
                            }catch (JSONException e)
                            {
                                phoneButton.setEnabled(true);
                                errorTextView.setText("Failed to connect to process request. Try again");
                                return;
                            }
                            connectDevice(phoneButton, deviceId,
                                    json1->{
                                        channelRegisterImageView.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.baseline_check_circle_outline_192));
                                        Account.getInstance().setHardware_id(hardwareId);
                                        errorTextView.setText("");
                                    },
                                    json1 ->{
                                        errorTextView.setText("Failed to connect to process request. Try again");
                                    });
                        },
                        json -> {
                            errorTextView.setText("Failed to connect to process request. Try again");
                        });
            });
        }
        else{
            phoneButton.setBackground(AppCompatResources.getDrawable(this, R.drawable.round_button_diabled));
        }
    }

    private void registerDevice(Button button, NetworkInterface success, NetworkInterface fail)
    {
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
        button.setEnabled(false);
        nrm.Put(getResources().getString(R.string.device_register_url), jsonObject,
                json -> {
                    button.setEnabled(true);
                    success.action(json);
                },
                json -> {
                    errorTextView.setText("Failed to connect to process request. Try again");
                    button.setEnabled(true);
                    fail.action(json);
                });
    }

    private void connectDevice(Button button, String device_id, NetworkInterface success, NetworkInterface fail) {
        JSONObject jsonObject = new JSONObject(
                Map.of(
                        "token", Account.getInstance().getTokenData().getValue(),
                        "device_id", device_id
                ));
        button.setEnabled(false);
        nrm.Put(R.string.livestream_connect_endpoint, jsonObject,
                json -> {
                    button.setEnabled(false);
                    phoneButton.setBackground(AppCompatResources.getDrawable(this, R.drawable.round_button_diabled));
                    success.action(json);
                },
                json -> {
                    errorTextView.setText("Failed to connect to process request. Try again");
                    button.setEnabled(true);
                    fail.action(json);
                });
    }

    private void email(Button button, NetworkRequestManager nrm, String device_id, NetworkInterface success, NetworkInterface fail)
    {
        JSONObject jsonObject = new JSONObject(
                Map.of(
                        "token", Account.getInstance().getTokenData().getValue(),
                        "device_id", device_id
                ));
        button.setEnabled(false);
        nrm.Post(R.string.device_email_endpoint, jsonObject,
                json -> {
                    button.setEnabled(true);
                    success.action(json);
                },
                json -> {
                    errorTextView.setText("Failed to connect to process request. Try again");
                    button.setEnabled(true);
                    fail.action(json);
                });
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}