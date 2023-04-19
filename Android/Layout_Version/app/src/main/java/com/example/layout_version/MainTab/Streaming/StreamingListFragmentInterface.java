package com.example.layout_version.MainTab.Streaming;

import com.example.layout_version.MainTab.Library.VideoItem;

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
                        return new ChannelItem(item.get("file_name").toString(), item.get("timestamp").toString(), item.getString("url"));
                    } catch (JSONException e) {
                        return new VideoItem("Unknown Video", "Failed to retrieve video file", null);
                    }
                })
                .collect(Collectors.toList());
    }
}
