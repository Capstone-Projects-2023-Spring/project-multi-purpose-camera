package com.example.layout_version;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    public ConstraintLayout main_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BackEnd.init_test_objects();

        ImageView btn;
        Button lib;
        TextView vid;
        WebView mWebView;

        ConstraintLayout constraintLayout = findViewById(R.id.main_layout);
        System.out.println("*******************\n\n\n" + constraintLayout);


        main_layout = new ConstraintLayout(this);
        main_layout.setLayoutParams(View_Factory.createLayoutParams(0, 0, 0, 0, -1, -1 ));
        constraintLayout.addView(main_layout);

        //ConstraintLayout camera_layout = construct_camera_layout(main_layout);

        btn = (ImageView) findViewById(R.id.settings);
        lib = (Button) findViewById(R.id.library);
        vid = (TextView) findViewById(R.id.cam_vid_clip);

        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (MainActivity.this,Settings.class);
                startActivity(intent);
            }
        });

        lib.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (MainActivity.this,Library.class);
                startActivity(intent);
            }
        });

        vid.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (MainActivity.this,Camera_Page.class);
                startActivity(intent);
            }
        });

        VideoView videoView = findViewById(R.id.video);

        MediaController mediaController = new MediaController (this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);

        // ------------- Video from project folder -------------
//        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.pain;
//        Uri uri = Uri.parse(videoPath);
//        videoView.setVideoURI(uri);

        // ------------- Video from online URL -------------
        Uri uri = Uri.parse("https://arbzc576ef.execute-api.us-east-1.amazonaws.com/milestone1?event_type=Video");
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();

        // ------------- Web Page from online and Local -------------
//        mWebView = (WebView) findViewById(R.id.video_web);
//        WebSettings webSettings = mWebView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//
//        mWebView.setWebViewClient(new Callback());
//        // mWebView.loadUrl("http://192.168.1.214:8082/");
//        mWebView.loadUrl("http://10.0.2.2:5500/Display-Character.html");
//        //mWebView.loadUrl("");
    }


//    private class Callback extends WebViewClient {
//        @Override
//        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
//            return false;
//        }
//    }
}