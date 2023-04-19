package com.example.layout_version.MainTab.Streaming;

public class ChannelItem {
    private String playbackUrl;
    private String deviceName;

    public ChannelItem(String playbackUrl, String deviceName) {
        this.playbackUrl = playbackUrl;
        this.deviceName = deviceName;
    }

    public String getPlaybackUrl() {
        return playbackUrl;
    }

    public void setPlaybackUrl(String playbackUrl) {
        this.playbackUrl = playbackUrl;
    }

    public String getDeviceName() {
        return deviceName;
    }
}
