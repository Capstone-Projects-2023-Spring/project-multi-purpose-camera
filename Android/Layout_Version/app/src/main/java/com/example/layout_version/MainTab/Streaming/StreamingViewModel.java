package com.example.layout_version.MainTab.Streaming;

import com.example.layout_version.MainTab.StateViewModel;

public class StreamingViewModel extends StateViewModel<ChannelItem> {
    public StreamingViewModel() {
        getDataList().add(ChannelItem.DEFAULT_CHANNEL_ITEM);
    }


    public void clearUpdate()
    {
        this.getDataList().clear();
        this.getDataList().add(ChannelItem.DEFAULT_CHANNEL_ITEM);
        super.clearUpdate();
    }
}