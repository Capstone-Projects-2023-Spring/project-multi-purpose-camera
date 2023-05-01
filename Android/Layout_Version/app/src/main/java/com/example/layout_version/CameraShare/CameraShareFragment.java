package com.example.layout_version.CameraShare;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.layout_version.Account.Account;
import com.example.layout_version.MainTab.Streaming.StreamingViewModel;
import com.example.layout_version.Network.NetworkRequestManager;
import com.example.layout_version.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class CameraShareFragment extends Fragment {

    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View layout = inflater.inflate(R.layout.fragment_camera_share, container, false);

        context = layout.getContext();

        Button shareButton = layout.findViewById(R.id.shareButton);
        TextView codeText = layout.findViewById(R.id.codeTextView);

        codeText.setVisibility(View.INVISIBLE);

        StreamingViewModel streamingViewModel = new ViewModelProvider(requireActivity()).get(StreamingViewModel.class);

        shareButton.setOnClickListener(view1 -> {
            NetworkRequestManager nrm = new NetworkRequestManager(context);
            JSONObject jsonObject = new JSONObject(Map.of(
                    "token", Account.getInstance().getTokenData().getValue(),
                    "device_id", streamingViewModel.getSelectedItem().getValue().getDeviceId()
            ));
            nrm.Post(R.string.device_share_endpoint, jsonObject,
                    json -> {
                        String code = null;
                        try {
                            code = json.getString("code");
                            codeText.setText(code);
                        } catch (JSONException e) {
                            codeText.setText("Error");
                        }
                        codeText.setVisibility(View.VISIBLE);
                    },
                    json->{
                        codeText.setText("Error");
                        codeText.setVisibility(View.VISIBLE);
                    });
        });

        return layout;
    }
}