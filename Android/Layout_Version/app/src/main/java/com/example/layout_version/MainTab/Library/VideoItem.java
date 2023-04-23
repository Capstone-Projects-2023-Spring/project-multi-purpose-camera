package com.example.layout_version.MainTab.Library;

public class VideoItem {
    private final String title;
    private final String description;

    private final String url;
    private final String thumbnailUrl;

    public static final VideoItem DEFAULT_VIDEO_ITEM = new VideoItem("Welcome to the Video Library", "Sign in first to view a list of videos", null, null);

    public VideoItem(String title, String description, String url, String thumbnailUrl) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
    public String getUrl() {
        return url;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
}
