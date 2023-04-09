package com.example.layout_version;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;


/**
 * The class Library_ videos_ page extends application compat activity. It displays the page to view library video
 */
public class Library_Videos_Page extends AppCompatActivity {

    String description;
    String date;
    String video;

    @Override

/**
 *
 * On create a video page template for when the user clicks on the Library_Video. The user can view video in this page.
 *
 * @param savedInstanceState  the saved instance state.
 */
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



    /**
     *
     * Video page
     *
     * @param description  the description.
     * @param date  the date.
     * @param video  the video.
     */
    public void VideoPage(String description, String date, String video)
    {

        this.description = description;
        this.date = date;
        this.video = video;
    }

}
