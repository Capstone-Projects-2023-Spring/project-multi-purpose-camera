package com.example.layout_version.MainTab.Streaming;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;

import com.example.layout_version.MainTab.Library.VideoItem;
import com.example.layout_version.MainTab.Library.VideoViewModel;
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

    static void setUpNetwork(Context context, LifecycleOwner lifecycleOwner, StreamingViewModel streamingViewModel)
    {
        streamingViewModel.getToken().observe(lifecycleOwner, token -> {
            if(token == null)
            {
                streamingViewModel.streamListUpdated();
                return;
            }
            JSONObject jsonObject = new JSONObject();
            try{
                jsonObject.put("token", token);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }

            NetworkRequestManager nrm = new NetworkRequestManager(context);
            nrm.Post(R.string.hardware_all_endpoint, jsonObject,
                    json -> {
                        Log.e("", "Load video list");
                        JSONArray hardwareArray;
                        try {
                            hardwareArray = json.getJSONArray("hardware");
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        List<ChannelItem> channels = StreamingListFragmentInterface.convertJSONArrayToChannel(hardwareArray);

                        streamingViewModel.setChannelList(channels);

                        streamingViewModel.streamListUpdated();
                    },
                    json -> {});
        });
    }
}
