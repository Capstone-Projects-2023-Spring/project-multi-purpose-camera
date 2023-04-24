package com.example.layout_version.MainTab.Streaming;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LifecycleOwner;

import com.example.layout_version.Account.Account;
import com.example.layout_version.MainTab.State.NetworkState;
import com.example.layout_version.Network.NetworkRequestManager;
import com.example.layout_version.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public interface StreamingFragmentInterface {

    static void loadData(Context context, StreamingViewModel streamingViewModel, ChannelItem channel, int retryNum)
    {
        RecordingState recordingState = streamingViewModel.getRecordingStateData().getValue();
        if(recordingState == RecordingState.BUFFERING)
        {
            Toast.makeText(context, "Fetching Stream Channels!! Please wait!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(retryNum <= 0)
        {
            streamingViewModel.setRecordingStatus(RecordingState.FAILED);
            return;
        }
        String token = Account.getInstance().getTokenData().getValue();
        if(token == null)
        {
            return;
        }
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("token", token);
            jsonObject.put("device_id", channel.getDeviceId());
        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        NetworkRequestManager nrm = new NetworkRequestManager(context);
        streamingViewModel.setRecordingStatus(RecordingState.BUFFERING);
        nrm.Post(R.string.is_recording_endpoint, jsonObject,
                json -> {

                    boolean message;
                    try {
                        message = json.getBoolean("message");
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    if(message)
                        streamingViewModel.setRecordingStatus(RecordingState.STARTED);
                    else
                        streamingViewModel.setRecordingStatus(RecordingState.STOPPED);
                },
                json -> {
                    streamingViewModel.setRecordingStatus(RecordingState.RETRY);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    loadData(context, streamingViewModel, channel, retryNum - 1);
                    Log.e("Retry", "Timestamp issue. Trying again");
                });
    }
    static void setUpNetwork(Context context, LifecycleOwner lifecycleOwner, StreamingViewModel streamingViewModel, int retryNum)
    {
        streamingViewModel.getSelectedItem().observe(lifecycleOwner, item  -> {
            loadData(context, streamingViewModel, item, retryNum);
        });
    }
}
