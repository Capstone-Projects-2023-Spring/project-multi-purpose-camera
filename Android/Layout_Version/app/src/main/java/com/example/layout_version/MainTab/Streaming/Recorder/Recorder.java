package com.example.layout_version.MainTab.Streaming.Recorder;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.layout_version.Account.Account;
import com.example.layout_version.MainTab.Streaming.StreamingViewModel;
import com.example.layout_version.Network.NetworkRequestManager;
import com.example.layout_version.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class Recorder {
    private final StreamingViewModel streamingViewModel;
    private final NetworkRequestManager nrm;

    private String deviceId;
    private String token;
    public Recorder(@NonNull Context context, @NonNull StreamingViewModel streamingViewModel)
    {
        nrm = new NetworkRequestManager(context);
        this.streamingViewModel = streamingViewModel;
    }

    public void start()
    {
        if(deviceId == null || token == null)
            return;
        streamingViewModel.setRecordingStatus(RecordingState.BUFFERING);
        Map<String, String> map = Map.of("token", token, "device_id", deviceId);
        JSONObject jsonObject= new JSONObject(map);
        nrm.Post(R.string.recording_start, jsonObject,
                json -> {
                    streamingViewModel.setRecordingStatus(RecordingState.STARTED);
                },
                json -> {
                    streamingViewModel.setRecordingStatus(RecordingState.FAILED);
                });
    }

    public void stop()
    {
        if(deviceId == null || token == null)
            return;
        streamingViewModel.setRecordingStatus(RecordingState.BUFFERING);
        Map<String, String> map = Map.of("token", token, "device_id", deviceId);
        JSONObject jsonObject= new JSONObject(map);
        nrm.Post(R.string.recording_stop, jsonObject,
                json -> {
                    streamingViewModel.setRecordingStatus(RecordingState.STOPPED);
                },
                json -> {
                    streamingViewModel.setRecordingStatus(RecordingState.FAILED);
                });
    }

    public void askIsRecording(int retryNum)
    {
        RecordingState recordingState = streamingViewModel.getRecordingStateData().getValue();
        if(recordingState == RecordingState.BUFFERING)
        {
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
        Map<String, String> map = Map.of("token", token, "device_id", deviceId);
        JSONObject jsonObject= new JSONObject(map);

        streamingViewModel.setRecordingStatus(RecordingState.BUFFERING);
        nrm.Post(R.string.is_recording_endpoint, jsonObject,
                json -> {
                    try {
                        if(json.getBoolean("message"))
                            streamingViewModel.setRecordingStatus(RecordingState.STARTED);
                        else
                            streamingViewModel.setRecordingStatus(RecordingState.STOPPED);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },
                json -> {
                    streamingViewModel.setRecordingStatus(RecordingState.RETRY);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    askIsRecording(retryNum - 1);
                });
    }

    public void setToken(@Nullable String token)
    {
        this.token = token;
    }
    public void setDeviceId(@Nullable String deviceId)
    {
        this.deviceId = deviceId;
    }

    public void clear()
    {
        this.token = null;
        this.deviceId = null;
    }
}
