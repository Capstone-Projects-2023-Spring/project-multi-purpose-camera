package com.example.layout_version.MainTab.Library;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;


public class VideoViewModel extends ViewModel {
    private List<VideoItem> videos;
    private final MutableLiveData<Integer> updateFlag;
    private final MutableLiveData<VideoItem> selectedVideo;

    public VideoViewModel() {
        updateFlag = new MutableLiveData<>(0);
        selectedVideo = new MutableLiveData<>(null);
        videos = new ArrayList<>();
        videos.add(VideoItem.DEFAULT_VIDEO_ITEM);
        Log.e("LiveViewModel Created", "LiveViewModel Created");
    }

    public LiveData<Integer> getUpdateFlag() {
        return updateFlag;
    }

    public void videoListUpdated()
    {
        updateFlag.setValue(updateFlag.getValue() + 1);
    }

    public MutableLiveData<VideoItem> getSelectedVideo() {
        return selectedVideo;
    }

    public void setSelectedVideo(VideoItem item)
    {
        selectedVideo.setValue(item);
    }

    public List<VideoItem> getVideoList()
    {
        return videos;
    }

    public void setVideoList(List<VideoItem> videos)
    {
        this.videos.clear();
        this.videos.addAll(videos);
    }

    public void clearUpdate()
    {
        this.videos.clear();
        this.videos.add(VideoItem.DEFAULT_VIDEO_ITEM);
        videoListUpdated();
    }
}
