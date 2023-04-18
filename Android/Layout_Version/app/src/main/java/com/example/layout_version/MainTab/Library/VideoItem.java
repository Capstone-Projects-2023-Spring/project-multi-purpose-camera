package com.example.layout_version.MainTab.Library;

public class VideoItem {
    private final String title;
    private final String description;

    private final String url;
    public VideoItem(String title, String description, String url) {
        this.title = title;
        this.description = description;
        this.url = url;
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
}
