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

    private final MutableLiveData<String> token;
    private final MutableLiveData<VideoItem> selectedVideo;
    private final VideoItem DEFAULT_VIDEO_ITEM = new VideoItem("Welcome to the Video Library", "Sign in first to view a list of videos", null);

    public VideoViewModel() {
        updateFlag = new MutableLiveData<>(0);
        token = new MutableLiveData<>(null);
        selectedVideo = new MutableLiveData<>(null);
        videos = new ArrayList<>();
        videos.add(DEFAULT_VIDEO_ITEM);
        Log.e("LiveViewModel Created", "LiveViewModel Created");
    }

    public LiveData<Integer> getUpdateFlag() {
        return updateFlag;
    }

    public void videoListUpdated()
    {
        updateFlag.setValue(updateFlag.getValue() + 1);
    }

    public MutableLiveData<String> getToken()
    {
        return token;
    }
    public void setToken(String token)
    {
        if(token == null)
        {
            this.videos.clear();
            videos.add(DEFAULT_VIDEO_ITEM);
        }

        this.token.setValue(token);
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
}
