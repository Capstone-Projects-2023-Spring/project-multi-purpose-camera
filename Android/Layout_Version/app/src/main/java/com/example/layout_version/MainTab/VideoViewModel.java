package com.example.layout_version.MainTab;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;


public class VideoViewModel extends ViewModel {
    private List<VideoItem> videos;
    private final MutableLiveData<Integer> updateFlag;

    public VideoViewModel() {
        updateFlag = new MutableLiveData<>(0);
        Log.e("LiveViewModel Created", "LiveViewModel Created");
    }

    public LiveData<Integer> getUpdateFlag() {
        return updateFlag;
    }

    public void videoListUpdated()
    {
        updateFlag.setValue(updateFlag.getValue() + 1);
    }

    public List<VideoItem> getVideoList()
    {
        return videos;
    }

    public void setVideoList(List<VideoItem> videos)
    {
        this.videos = videos;
    }


}
