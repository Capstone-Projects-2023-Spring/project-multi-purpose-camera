package com.example.layout_version.MainTab.Streaming;

public class ChannelItem {
    private final String deviceId;
    private final String playbackUrl;
    private final String deviceName;
    private final String maxResolution;
    private final String recordingPrefix;
    private final String arn;
    public static final ChannelItem DEFAULT_CHANNEL_ITEM = new ChannelItem(null, null, "Sign in first to view a list of videos", "720p", null, null);


    public ChannelItem(String deviceId, String playbackUrl, String deviceName, String maxResolution, String recordingPrefix, String arn) {
        this.deviceId =deviceId;
        this.playbackUrl = playbackUrl;
        this.deviceName = deviceName;
        this.maxResolution = maxResolution;
        this.recordingPrefix = recordingPrefix;
        this.arn = arn;
    }
    public String getDeviceId() {
        return deviceId;
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

    public String getArn()
    {
        return arn;
    }
}
