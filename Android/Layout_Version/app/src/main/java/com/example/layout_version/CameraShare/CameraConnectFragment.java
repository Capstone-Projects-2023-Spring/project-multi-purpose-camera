package com.example.layout_version.CameraShare;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.layout_version.Account.Account;
import com.example.layout_version.Network.NetworkRequestManager;
import com.example.layout_version.R;

import org.json.JSONObject;

import java.util.Map;

public class CameraConnectFragment extends Fragment {
    private Context context;
    private Button connectButton;
    private EditText codeEditText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_camera_connect, container, false);
        context = layout.getContext();
        connectButton = layout.findViewById(R.id.connectButton);
        codeEditText = layout.findViewById(R.id.codeEdit);

        codeEditText.setText("");

        connectButton.setOnClickListener(view1 -> {
            Log.e("Connect", "here");

            Log.e("Share", "here");
            NetworkRequestManager nrm = new NetworkRequestManager(context);
            JSONObject jsonObject = new JSONObject(Map.of(
                    "token", Account.getInstance().getTokenData().getValue(),
                    "code", codeEditText.getText().toString()
            ));
            nrm.Post(R.string.device_code_endpoint, jsonObject,
                    json -> {
                        Toast.makeText(context, "Successfully connected device to account", Toast.LENGTH_SHORT).show();
                        if(getParentFragment() instanceof CameraShareInterface)
                        {
                            ((CameraShareInterface)getParentFragment()).action();
                        }
                    },
                    json->{
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    });
        });

        return layout;
    }

    interface CameraShareInterface{
        void action();
    }
}