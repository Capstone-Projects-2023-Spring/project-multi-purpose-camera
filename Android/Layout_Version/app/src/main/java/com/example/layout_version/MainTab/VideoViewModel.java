package com.example.layout_version.MainTab;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;




public class VideoViewModel extends ViewModel {
    private final MutableLiveData<VideoState> vidState;


    public VideoViewModel() {
        vidState = new MutableLiveData<>(new VideoState("Sample", "Sample"));
        Log.e("LiveViewModel Created", "LiveViewModel Created");
    }

    public LiveData<VideoState> getVideoState() {
        return vidState;
    }

    public void setVidState(VideoState state)
    {
        vidState.setValue(state);
    }
}
