package com.example.layout_version;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class Library_Video extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.library_videos);

        VideoView videoView = findViewById(R.id.saved_video);

        MediaController mediaController = new MediaController (this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);

        Uri uri = Uri.parse("");
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();

    }

    public void savedVideo (VideoView video, TextView textView) {
        VideoView v = findViewById(R.id.saved_video);
        TextView b = findViewById(R.id.video_description);

    }
}
