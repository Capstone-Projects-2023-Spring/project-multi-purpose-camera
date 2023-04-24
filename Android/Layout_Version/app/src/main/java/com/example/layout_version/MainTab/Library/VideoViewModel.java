package com.example.layout_version.MainTab.Library;

import android.util.Log;

import com.example.layout_version.MainTab.StateViewModel;

import java.util.ArrayList;
import java.util.List;


public class VideoViewModel extends StateViewModel<VideoItem> {

    public VideoViewModel() {
        getDataList().add(VideoItem.DEFAULT_VIDEO_ITEM);
    }

    public void clearUpdate()
    {
        getDataList().clear();
        getDataList().add(VideoItem.DEFAULT_VIDEO_ITEM);
        super.clearUpdate();
    }
}
