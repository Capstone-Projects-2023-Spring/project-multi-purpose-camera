package com.example.layout_version.MainTab.Streaming;

public class ChannelItem {
    private final String playbackUrl;
    private final String deviceName;
    private final String maxResolution;
    private final String recordingPrefix;

    public ChannelItem(String playbackUrl, String deviceName, String maxResolution, String recordingPrefix) {
        this.playbackUrl = playbackUrl;
        this.deviceName = deviceName;
        this.maxResolution = maxResolution;
        this.recordingPrefix = recordingPrefix;
    }

    public String getPlaybackUrl() {
        return playbackUrl;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getMaxResolution() {
        return maxResolution;
    }

    public String getRecordingPrefix() {
        return recordingPrefix;
    }
}
