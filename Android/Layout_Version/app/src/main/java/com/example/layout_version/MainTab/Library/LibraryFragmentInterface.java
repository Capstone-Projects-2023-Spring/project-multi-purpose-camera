package com.example.layout_version.MainTab.Library;

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

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public interface LibraryFragmentInterface {
    void videoSelected();

    static List<VideoItem> convertJSONArrayToVideoItem(JSONArray jsonArray)
    {
        return IntStream.range(0,jsonArray.length())
                .mapToObj(i -> {
                    try {
                        JSONObject item = jsonArray.getJSONObject(i);
                        return new VideoItem(item.get("file_name").toString(), item.get("timestamp").toString(), item.getString("url"), item.getString("thumbnail"));
                    } catch (JSONException e) {
                        return new VideoItem("Unknown Video", "Failed to retrieve video file", null, null);
                    }
                })
                .collect(Collectors.toList());
    }

    static void loadData(Context context, VideoViewModel videoViewModel, String token, int retryNum)
    {
        NetworkState networkState = videoViewModel.getStateData().getValue();
        if(networkState == NetworkState.REQUESTED || networkState == NetworkState.LOADING)
        {
            Toast.makeText(context, "Fetching Video Library!! Please wait!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(retryNum <= 0)
        {
            videoViewModel.setStateData(NetworkState.FAILED);
            return;
        }
        if(token == null)
        {
            videoViewModel.clearUpdate();
            return;
        }
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("token", token);
            jsonObject.put("timestamp", Account.getInstance().getTimestamp());
            jsonObject.put("username", Account.getInstance().getUsername());
            Log.e("Video", "Token in JSON");
        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        NetworkRequestManager nrm = new NetworkRequestManager(context);
        videoViewModel.setStateData(NetworkState.REQUESTED);
        nrm.Post(R.string.file_all_endpoint, jsonObject,
                json -> {
                    videoViewModel.setStateData(NetworkState.LOADING);
                    Log.e("", "Load video list");
                    JSONArray fileArray;
                    List<VideoItem> videos;
                    try {
                        fileArray = json.getJSONArray("files");
                        videos = LibraryFragmentInterface.convertJSONArrayToVideoItem(fileArray);
                    } catch (JSONException e) {
                        videos = Collections.singletonList(VideoItem.DEFAULT_VIDEO_ITEM);
                    }

                    videoViewModel.setDataList(videos);
                    videoViewModel.setStateData(NetworkState.LOADED);
                },
                json -> {
                    videoViewModel.setStateData(NetworkState.RETRY);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    loadData(context, videoViewModel, token, retryNum - 1);
                    Log.e("Retry", "Timestamp issue. Trying again");
                });
    }
    static void setUpNetwork(Context context, LifecycleOwner lifecycleOwner, VideoViewModel videoViewModel, int retryNum)
    {
        Account.getInstance().getTokenData().observe(lifecycleOwner, token  -> {
            loadData(context, videoViewModel, token, retryNum);
        });
    }
}
