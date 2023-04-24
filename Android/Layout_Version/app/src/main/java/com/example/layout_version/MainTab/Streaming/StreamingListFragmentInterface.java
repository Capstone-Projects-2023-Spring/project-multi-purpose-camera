package com.example.layout_version.MainTab.Streaming;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LifecycleOwner;

import com.example.layout_version.Account.Account;
import com.example.layout_version.MainTab.State;
import com.example.layout_version.Network.NetworkRequestManager;
import com.example.layout_version.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public interface StreamingListFragmentInterface {
    void channelSelected(ChannelItem channelItem);

    static List<ChannelItem> convertJSONArrayToChannel(JSONArray jsonArray)
    {
        return IntStream.range(0,jsonArray.length())
                .mapToObj(i -> {
                    try {
                        JSONObject item = jsonArray.getJSONObject(i);
                        return new ChannelItem(item.get("playback_url").toString(), item.get("device_name").toString(), item.get("max_resolution").toString(), item.get("s3_recording_prefix").toString());
                    } catch (JSONException e) {
                        return new ChannelItem("Unknown Video", "Failed to retrieve video file",  "720p", null);
                    }
                })
                .collect(Collectors.toList());
    }

    static void loadData(Context context, StreamingViewModel streamingViewModel, String token, int retryNum)
    {
        State state = streamingViewModel.getStateData().getValue();
        if(state == State.REQUESTED || state == State.LOADING)
        {
            Toast.makeText(context, "Fetching Stream Channels!! Please wait!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(retryNum <= 0)
        {
            streamingViewModel.setStateData(State.FAILED);
            return;
        }
        if(token == null)
        {
            streamingViewModel.clearUpdate();
            return;
        }
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("token", token);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        NetworkRequestManager nrm = new NetworkRequestManager(context);
        streamingViewModel.setStateData(State.REQUESTED);
        nrm.Post(R.string.hardware_all_endpoint, jsonObject,
                json -> {
                    streamingViewModel.setStateData(State.LOADING);
                    Log.e("", "Load video list");
                    JSONArray hardwareArray;
                    try {
                        hardwareArray = json.getJSONArray("hardware");
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    List<ChannelItem> channels = StreamingListFragmentInterface.convertJSONArrayToChannel(hardwareArray);

                    streamingViewModel.setDataList(channels);
                    streamingViewModel.setStateData(State.LOADED);
                },
                json -> {
                    streamingViewModel.setStateData(State.RETRY);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    loadData(context, streamingViewModel, token, retryNum - 1);
                    Log.e("Retry", "Timestamp issue. Trying again");
                });
    }
    static void setUpNetwork(Context context, LifecycleOwner lifecycleOwner, StreamingViewModel streamingViewModel, int retryNum)
    {
        Account.getInstance().getTokenData().observe(lifecycleOwner, token  -> {
            loadData(context, streamingViewModel, token, retryNum);
        });
    }
}
