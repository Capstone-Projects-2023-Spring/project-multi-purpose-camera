package com.example.layout_version.MainTab.Library;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;

import com.example.layout_version.Network.NetworkRequestManager;
import com.example.layout_version.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
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
                        return new VideoItem(item.get("file_name").toString(), item.get("timestamp").toString(), item.getString("url"));
                    } catch (JSONException e) {
                        return new VideoItem("Unknown Video", "Failed to retrieve video file", null);
                    }
                })
                .collect(Collectors.toList());
    }

    static void setUpNetwork(Context context, LifecycleOwner lifecycleOwner, VideoViewModel videoViewModel)
    {
        videoViewModel.getToken().observe(lifecycleOwner, token -> {
            if(token == null)
            {
                videoViewModel.videoListUpdated();
                return;
            }
            JSONObject jsonObject = new JSONObject();
            try{
                jsonObject.put("token", token);
                Log.e("Video", "Token in JSON");
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            Log.e("TOKEN", token);
            NetworkRequestManager nrm = new NetworkRequestManager(context);
            nrm.Post(R.string.file_all_endpoint, jsonObject,
                    json -> {
                        Log.e("", "Load video list");
                        JSONArray fileArray;
                        List<VideoItem> videos;
                        try {
                            fileArray = json.getJSONArray("files");
                            videos = LibraryFragmentInterface.convertJSONArrayToVideoItem(fileArray);
                        } catch (JSONException e) {
                            videos = Collections.singletonList(VideoItem.DEFAULT_VIDEO_ITEM);
                        }


                        videoViewModel.setVideoList(videos);

                        videoViewModel.videoListUpdated();
                    },
                    json -> {});
        });
    }
}
