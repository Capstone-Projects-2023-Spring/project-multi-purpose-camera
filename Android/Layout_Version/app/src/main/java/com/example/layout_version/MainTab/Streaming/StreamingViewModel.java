package com.example.layout_version.MainTab.Streaming;

import androidx.lifecycle.MutableLiveData;

import com.example.layout_version.MainTab.NetworkStateViewModel;

public class StreamingViewModel extends NetworkStateViewModel<ChannelItem> {
    private final MutableLiveData<RecordingStatus> recordingStatusData;
    public StreamingViewModel() {
        getDataList().add(ChannelItem.DEFAULT_CHANNEL_ITEM);
        recordingStatusData = new MutableLiveData<>(RecordingStatus.STOPPED);
    }

    public MutableLiveData<RecordingStatus> getRecordingStatusData() {
        return recordingStatusData;
    }

    public void setRecordingStatus(RecordingStatus status)
    {
        recordingStatusData.setValue(status);
    }

    public void clearUpdate()
    {
        this.getDataList().clear();
        this.getDataList().add(ChannelItem.DEFAULT_CHANNEL_ITEM);
        super.clearUpdate();
    }
}