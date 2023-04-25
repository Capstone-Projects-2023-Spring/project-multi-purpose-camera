package com.example.layout_version.MainTab.Streaming;

import androidx.lifecycle.MutableLiveData;

import com.example.layout_version.MainTab.State.NetworkStateViewModel;
import com.example.layout_version.MainTab.Streaming.Recorder.RecordingState;

public class StreamingViewModel extends NetworkStateViewModel<ChannelItem> {
    private final MutableLiveData<RecordingState> recordingStateData;
    public StreamingViewModel() {
        getDataList().add(ChannelItem.DEFAULT_CHANNEL_ITEM);
        recordingStateData = new MutableLiveData<>(RecordingState.STOPPED);
    }

    public MutableLiveData<RecordingState> getRecordingStateData() {
        return recordingStateData;
    }

    public void setRecordingStatus(RecordingState status)
    {
        recordingStateData.setValue(status);
    }

    public void clearUpdate()
    {
        this.getDataList().clear();
        this.getDataList().add(ChannelItem.DEFAULT_CHANNEL_ITEM);
        super.clearUpdate();
    }
}