package com.example.layout_version.MainTab.Streaming;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.layout_version.MainTab.Library.VideoItem;

import java.util.ArrayList;
import java.util.List;

public class StreamingViewModel extends ViewModel {
    private List<ChannelItem> channels;
    private final MutableLiveData<Integer> updateFlag;
    private final MutableLiveData<ChannelItem> selectedChannel;
    public StreamingViewModel() {
        updateFlag = new MutableLiveData<>(0);
        selectedChannel = new MutableLiveData<>(null);
        channels = new ArrayList<>();
        channels.add(ChannelItem.DEFAULT_CHANNEL_ITEM);
    }

    public LiveData<Integer> getUpdateFlag() {
        return updateFlag;
    }

    public void streamListUpdated()
    {
        updateFlag.setValue(updateFlag.getValue() + 1);
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
        streamListUpdated();
    }

    public void clearUpdate()
    {
        this.channels.clear();
        this.channels.add(ChannelItem.DEFAULT_CHANNEL_ITEM);
        streamListUpdated();
    }
}