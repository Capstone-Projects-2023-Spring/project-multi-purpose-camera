package com.example.layout_version.MainTab;

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

    public VideoViewModel() {
        updateFlag = new MutableLiveData<>(0);
        token = new MutableLiveData<>(null);
        videos = new ArrayList<>();
        videos.add(new VideoItem("Welcome to the Video Library", "Sign in first to view a list of videos"));
        Log.e("LiveViewModel Created", "LiveViewModel Created");
    }

    public LiveData<Integer> getUpdateFlag() {
        return updateFlag;
    }

    public void videoListUpdated()
    {
        updateFlag.setValue(updateFlag.getValue() + 1);
    }

    public LiveData<String> getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token.setValue(token);
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
