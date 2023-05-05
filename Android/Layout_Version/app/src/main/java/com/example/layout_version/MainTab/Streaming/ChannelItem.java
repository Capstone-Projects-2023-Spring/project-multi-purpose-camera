package com.example.layout_version.MainTab.Streaming;

public class ChannelItem {
    private final String deviceId;
    private final String playbackUrl;
    private final String deviceName;
    private final String maxResolution;
    private final String recordingPrefix;
    private final String arn;
    private final String hardware_id;
    private final String ingestEndpoint;
    private final String streamKey;

    public static final ChannelItem DEFAULT_CHANNEL_ITEM = new ChannelItem(null, null, "Sign in first to view a list of videos", "720p", null, null, null, null, null);


    public ChannelItem(String deviceId, String playbackUrl, String deviceName, String maxResolution, String recordingPrefix, String arn, String hardware_id, String ingestEndpoint, String streamKey) {
        this.deviceId =deviceId;
        this.playbackUrl = playbackUrl;
        this.deviceName = deviceName;
        this.maxResolution = maxResolution;
        this.recordingPrefix = recordingPrefix;
        this.arn = arn;
        this.hardware_id = hardware_id;
        this.ingestEndpoint =ingestEndpoint;
        this.streamKey = streamKey;
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

    public String getHardware_id()
    {
        return hardware_id;
    }

    public String getIngestEndpoint() {
        return ingestEndpoint;
    }

    public String getStreamKey() {
        return streamKey;
    }
}
