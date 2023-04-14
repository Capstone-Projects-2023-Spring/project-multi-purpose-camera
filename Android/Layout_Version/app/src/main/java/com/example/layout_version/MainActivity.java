package com.example.layout_version;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import android.webkit.WebSettings;

import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.TextView;

import com.example.layout_version.Account.Account;
import com.example.layout_version.Account.Account_Page;
import com.example.layout_version.Account.Account_Page_Profile;

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
//        vidclip = (TextView) findViewById(R.id.cam_vid_clip);

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
                Account account = Account.getInstance();
                Intent intent;
                if(account.isSignedIn())
                    intent = new Intent (MainActivity.this, Account_Page_Profile.class);
                else
                    intent = new Intent (MainActivity.this, Account_Page.class);

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


//        vidclip.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent (MainActivity.this,Camera_Page.class);
//                startActivity(intent);
//            }
//        });

        // LIVESTREAM STUFF
        /*
        ImageView livestream = findViewById(R.id.livestreamImageView);
        livestream.setImageDrawable(getResources().getDrawable(R.drawable.account_setting));
        Receiver_Client client = new Receiver_Client(livestream);
        client.execute();

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
        */
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