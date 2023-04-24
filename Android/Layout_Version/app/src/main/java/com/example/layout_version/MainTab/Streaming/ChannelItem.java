package com.example.layout_version.MainTab.Streaming;

public class ChannelItem {
    private final String playbackUrl;
    private final String deviceName;
    private final String maxResolution;
    private final String recordingPrefix;
    public static final ChannelItem DEFAULT_CHANNEL_ITEM = new ChannelItem(null, "Sign in first to view a list of videos", "720p", null);


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
