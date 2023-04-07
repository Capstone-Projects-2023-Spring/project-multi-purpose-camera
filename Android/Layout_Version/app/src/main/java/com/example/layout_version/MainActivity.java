package com.example.layout_version;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebSettings;

import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import android.widget.TextView;

import java.util.concurrent.ExecutionException;

//import org.opencv.highgui.HighGui;


public class MainActivity extends AppCompatActivity /*implements CameraBridgeViewBase.CvCameraViewListener2*/{

    public ConstraintLayout main_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        MyAsyncTask database = new MyAsyncTask(() -> {
            System.out.println("calling backend");
            BackEnd.init();
        });
        try {
            System.out.println("running async");
            database.execute();
            database.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        //init_camera_view();
        ImageView btn;
        ImageView account;
        Button lib;
        TextView vid;
        WebView mWebView;

        ConstraintLayout constraintLayout = findViewById(R.id.main_layout);
        System.out.println("*******************\n\n\n" + constraintLayout);

        main_layout = new ConstraintLayout(this);
        main_layout.setLayoutParams(View_Factory.createLayoutParams(0, 0, 0, 0, -1, -1 ));
        constraintLayout.addView(main_layout);


        btn = (ImageView) findViewById(R.id.settings);
        account = (ImageView) findViewById(R.id.account);
        lib = (Button) findViewById(R.id.library);
//        vid = (TextView) findViewById(R.id.cam_vid_clip);

        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (MainActivity.this,Settings.class);
                startActivity(intent);
            }
        });

        account.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (MainActivity.this,Account_Page.class);
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


//        vid.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent (MainActivity.this,Camera_Page.class);
//                startActivity(intent);
//            }
//        });
        Uri uri = Uri.parse("http://10.0.2.2:9999/");

//        vid.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent (MainActivity.this,Camera_Page.class);
//                startActivity(intent);
//                System.out.println("--------Entering live stream");
//            }
//        });

        // ------------- Layout fir reuseable navbar ---------------
//        RelativeLayout container = findViewById(R.id.nav_test);
//        ViewGroup nav = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.main_top_nav_bar, null);
//        container.addView(nav);
//
//        VideoView videoView = findViewById(R.id.video);

//        MediaController mediaController = new MediaController (this);
//        videoView.setMediaController(mediaController);
//        mediaController.setAnchorView(videoView);

        // ------------- Video from project folder -------------
//        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.pain;
//        Uri uri = Uri.parse(videoPath);
//        videoView.setVideoURI(uri);

        // ------------- Video from online URL -------------
        //Uri uri = Uri.parse("https://arbzc576ef.execute-api.us-east-1.amazonaws.com/milestone1?event_type=Video");
        // Livestream online and server
        //Uri uri = Uri.parse("http://44.212.17.188:9999/");
//        Uri uri = Uri.parse("http://10.0.2.2:9999/");
//        //Uri uri = Uri.parse("https://livestream.com/accounts/11707815/events/4299357");
//        videoView.setVideoURI(uri);
//        videoView.requestFocus();
//        videoView.start();

        // ------------- Web Page from online and Local -------------
        mWebView = (WebView) findViewById(R.id.video_web);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        mWebView.setWebViewClient(new Callback());
        mWebView.loadUrl("http://192.168.137.247:8082/");
//          mWebView.loadUrl("http://192.168.87.249:5500/Display-Character.html");
////        mWebView.loadUrl("http://44.212.17.188:9999/");

        //        String targetServer = "http://10.0.2.2:9999/";
        //        AsyncTaskRunner ad = new AsyncTaskRunner();
        //        ad.execute(targetServer);
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

//        ImageView image = (ImageView) findViewById(R.id.image);
//        Receiver_Client.yourImageView = image;
//        AsyncTaskRunner thread = new AsyncTaskRunner();
//        thread.execute("");


    }




    private class Callback extends WebViewClient {
        @Override
        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
            return false;
        }
    }



    private class MyAsyncTask extends AsyncTask<Void, Void, Void> {
        private Runnable task = null;
        MyAsyncTask(Runnable task) {
            this.task = task;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            System.out.println("doing in background");
            task.run();
            return null;
        }
    }
    //endregion
    private class AsyncTaskRunner extends AsyncTask<String, String, String> {
        private String resp;
        ProgressDialog progressDialog;
        @Override
        protected String doInBackground(String... params) {
            System.out.println("asnc task working");
            return resp;
        }
        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
        }
    }

}