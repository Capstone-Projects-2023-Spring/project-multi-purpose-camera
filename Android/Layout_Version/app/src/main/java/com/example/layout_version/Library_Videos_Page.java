package com.example.layout_version;


import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class Library_Videos_Page extends AppCompatActivity {

    String description;
    String date;
    String video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.library_videos_page);

        VideoView videoView = findViewById(R.id.video);
        String videoPath = "https://mpc-capstone.s3.amazonaws.com/_import_616e710b7f2ff0.35776522_preview.mp4";
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);

        MediaController mediaController = new MediaController (this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
        videoView.requestFocus();
        videoView.start();

    }

}
