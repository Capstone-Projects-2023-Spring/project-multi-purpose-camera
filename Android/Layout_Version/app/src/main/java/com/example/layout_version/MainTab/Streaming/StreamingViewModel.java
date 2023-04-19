package com.example.layout_version.MainTab.Streaming;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class StreamingViewModel extends ViewModel {
    private List<ChannelItem> channels;
    private final MutableLiveData<Integer> updateFlag;

    private final MutableLiveData<String> token;
    private final MutableLiveData<ChannelItem> selectedChannel;
    private final ChannelItem DEFAULT_CHANNEL_ITEM = new ChannelItem(null, "Sign in first to view a list of videos", "720p", null);

    public StreamingViewModel() {
        updateFlag = new MutableLiveData<>(0);
        token = new MutableLiveData<>(null);
        selectedChannel = new MutableLiveData<>(null);
        channels = new ArrayList<>();
        channels.add(DEFAULT_CHANNEL_ITEM);
        Log.e("LiveViewModel Created", "LiveViewModel Created");
    }

    public LiveData<Integer> getUpdateFlag() {
        return updateFlag;
    }

    public void streamListUpdated()
    {
        updateFlag.setValue(updateFlag.getValue() + 1);
    }

    public LiveData<String> getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        if(token == null)
        {
            this.channels.clear();
            channels.add(DEFAULT_CHANNEL_ITEM);
        }

        this.token.setValue(token);
    }

    public MutableLiveData<ChannelItem> getSelectedChannel() {
        return selectedChannel;
    }

    public void setSelectedChannel(ChannelItem item)
    {
        selectedChannel.setValue(item);
    }

    public List<ChannelItem> getChannelList()
    {
        return channels;
    }

    public void setChannelList(List<ChannelItem> channels)
    {
        this.channels.clear();
        this.channels.addAll(channels);
    }

}