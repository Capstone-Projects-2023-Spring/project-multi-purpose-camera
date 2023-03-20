package com.example.layout_version;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class Library extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.library);

        ImageView btn;
        Button view;

        btn = (ImageView) findViewById(R.id.settings);
        view = (Button) findViewById(R.id.view);

//        VideoView videoView = findViewById(R.id.video);
//        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.pain;
//        Uri uri = Uri.parse(videoPath);
//        videoView.setVideoURI(uri);
//
//        MediaController mediaController = new MediaController (this);
//        videoView.setMediaController(mediaController);
//        mediaController.setAnchorView(videoView);

        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Library.this,Settings.class);
                startActivity(intent);
            }
        });

        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Library.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
