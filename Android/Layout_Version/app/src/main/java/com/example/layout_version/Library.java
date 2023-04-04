package com.example.layout_version;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.net.URI;

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

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        RelativeLayout container = findViewById(R.id.captured_videos);
        int marg = 0;

        // The reuseable layout for libraries
        ViewGroup myLayout = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.library_videos, null);
        container.addView(myLayout);

        ViewGroup myLayout2 = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.library_videos, null);
        params.setMargins(0, 230, 0, 10);
        myLayout2.setLayoutParams(params);
        container.addView(myLayout2);

        ViewGroup myLayout3 = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.library_videos, null);
        params.setMargins(0, 460, 0, 10);
        myLayout3.setLayoutParams(params);
        container.addView(myLayout3);

        ViewGroup myLayout4 = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.library_videos, null);
        params.setMargins(0, 690, 0, 10);
        myLayout4.setLayoutParams(params);
        container.addView(myLayout4);

        ViewGroup myLayout5 = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.library_videos, null);
        params.setMargins(0, 920, 0, 10);
        myLayout5.setLayoutParams(params);
        container.addView(myLayout5);

        ViewGroup myLayout6 = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.library_videos, null);
        params.setMargins(0, 1150, 0, 10);
        myLayout6.setLayoutParams(params);
        container.addView(myLayout6);

        ViewGroup myLayout7= (ViewGroup) LayoutInflater.from(this).inflate(R.layout.library_videos, null);
        params.setMargins(0, 1380, 0, 10);
        myLayout7.setLayoutParams(params);
        container.addView(myLayout7);


//        String url = "http://example.com/data.json";
//        HttpClient client = HttpClient.newHttpClient();
//        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        String json = response.body();

//        VideoView videoView = new VideoView(this);
//        MediaController mediaController = new MediaController (this);
//        videoView.setMediaController(mediaController);
//        mediaController.setAnchorView(videoView);
//        Uri uri = Uri.parse("https://mpc-capstone.s3.amazonaws.com/gatoilse-cat-love.mp4");
//        videoView.setVideoURI(uri);
//        videoView.requestFocus();
//        videoView.start();
//        myLayout.addView(videoView);


//        for (int i = 0; i < 5; i++)
//        {
//            ViewGroup myLayout = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.library_videos, null);
//            params.setMargins(0, marg, 0, 0);
//            myLayout.setLayoutParams(params);
//            container.addView(myLayout);
//            params.setMargins(0, marg+230, 0, 0);
//        }

    }

}