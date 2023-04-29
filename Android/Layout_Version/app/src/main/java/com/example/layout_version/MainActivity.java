package com.example.layout_version;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.Manifest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.layout_version.Account.Account;
import com.example.layout_version.Account.Account_Page;
import com.example.layout_version.Account.Account_Page_Profile;
import com.example.layout_version.Bluetooth.BluetoothManager;
import com.example.layout_version.MainTab.Library.LibraryFragment;
import com.example.layout_version.MainTab.Library.LibraryFragmentInterface;
import com.example.layout_version.MainTab.Library.VideoDetailFragment;
import com.example.layout_version.MainTab.Library.VideoViewModel;
import com.example.layout_version.MainTab.Streaming.ChannelItem;
import com.example.layout_version.MainTab.Streaming.StreamingFragment;
import com.example.layout_version.MainTab.Streaming.StreamingListFragment;
import com.example.layout_version.MainTab.Streaming.StreamingListFragmentInterface;
import com.example.layout_version.MainTab.Streaming.StreamingViewModel;


public class MainActivity extends AppCompatActivity implements LibraryFragmentInterface, StreamingListFragmentInterface {

    private Fragment libraryFragment;
    private VideoViewModel videoViewModel;
    private StreamingViewModel streamingViewModel;
    private Account account;
    private Button libraryTabButton;
    private Button cameraTabButton;

    private boolean videoDetailViewFlag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videoDetailViewFlag = false;

        videoViewModel = new ViewModelProvider(this).get(VideoViewModel.class);
        LibraryFragmentInterface.setUpNetwork(this, this, videoViewModel, 4);
        streamingViewModel = new ViewModelProvider(this).get(StreamingViewModel.class);
        StreamingListFragmentInterface.setUpNetwork(this, this, streamingViewModel, 4);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("My_Notification", "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        Notifications notif = new Notifications(this);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        notif.send_Recording_Notification( managerCompat);
        notif.send_New_Account_Notification( managerCompat);
        notif.send_Motion_Detected_Notification( managerCompat);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

//        MyAsyncTask database = new MyAsyncTask(() -> {
//            System.out.println("calling backend");
//            BackEnd.init();
//        });
//        try {
//            System.out.println("running async");
//            database.execute();
//            database.get();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }

        ImageView btn = findViewById(R.id.settings);
        ImageView accountImageView = findViewById(R.id.account);
        libraryTabButton = findViewById(R.id.library);
        cameraTabButton = findViewById(R.id.view);

        account = Account.getInstance();
        if(!account.isSignedIn())
        {
            Intent intent = new Intent (MainActivity.this, Account_Page.class);
            startActivity(intent);
        }

//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//
//        MyAsyncTask database = new MyAsyncTask(() -> {
//            System.out.println("calling backend");
//            BackEnd.init();
//        });
//        try {
//            System.out.println("running async");
//            database.execute();
//            database.get();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }

        btn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Settings.class);
            startActivity(intent);
        });

        accountImageView.setOnClickListener(view -> {

            Intent intent;
            if(account.isSignedIn())
                intent = new Intent (MainActivity.this, Account_Page_Profile.class);
            else
                intent = new Intent (MainActivity.this, Account_Page.class);

            startActivity(intent);
        });

        if(savedInstanceState == null) {
            Log.d("", "New state");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.mainFragmentContainerView, StreamingListFragment.newInstance())
                    .commit();
            libraryFragment =LibraryFragment.newInstance(true);
        }
        else
            Log.d("", "Npot New state");



        libraryTabButton.setOnClickListener(view -> {
            LibraryFragment fragment = (LibraryFragment)getSupportFragmentManager().findFragmentByTag("LibraryFragment");
            if (fragment != null && fragment.isVisible()) {
                return;
            }
            VideoDetailFragment videoDetailFragment = (VideoDetailFragment)getSupportFragmentManager().findFragmentByTag("VideoDetailFragment");
            if (videoDetailFragment != null && videoDetailFragment.isVisible()) {
                getSupportFragmentManager().popBackStack();
                videoDetailViewFlag = false;
                return;
            }
            cameraTabButton.setBackgroundColor(Color.parseColor("#ffffff"));
            libraryTabButton.setBackgroundColor(Color.parseColor("#c4fffd"));
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainFragmentContainerView, libraryFragment, "LibraryFragment")
                    .addToBackStack("LibraryFragment")
                    .commit();
            if(videoDetailViewFlag)
            {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.mainFragmentContainerView, new VideoDetailFragment(), "VideoDetailFragment")
                        .addToBackStack("VideoDetailFragment")
                        .commit();
            }
        }
        );

        cameraTabButton.setOnClickListener(view -> {
            LibraryFragment fragment = (LibraryFragment)getSupportFragmentManager().findFragmentByTag("LibraryFragment");
            StreamingFragment streamingFragment = (StreamingFragment) getSupportFragmentManager().findFragmentByTag("StreamingFragment");
            if (fragment != null && fragment.isVisible() || streamingFragment != null && streamingFragment.isVisible()) {
                getSupportFragmentManager().popBackStack();
            }
            VideoDetailFragment videoDetailFragment = (VideoDetailFragment)getSupportFragmentManager().findFragmentByTag("VideoDetailFragment");
            if (videoDetailFragment != null && videoDetailFragment.isVisible()) {
                getSupportFragmentManager().popBackStack("LibraryFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }

            libraryTabButton.setBackgroundColor(Color.parseColor("#ffffff"));
            cameraTabButton.setBackgroundColor(Color.parseColor("#c4fffd"));
        });
    }

    public void onBackPressed()
    {
        super.onBackPressed();
        LibraryFragment fragment = (LibraryFragment)getSupportFragmentManager().findFragmentByTag("LibraryFragment");
        VideoDetailFragment videoDetailFragment = (VideoDetailFragment)getSupportFragmentManager().findFragmentByTag("VideoDetailFragment");
        if (fragment != null && fragment.isVisible() ||
                videoDetailFragment != null && videoDetailFragment.isVisible()) {
            libraryTabButton.setBackgroundColor(Color.parseColor("#c4fffd"));
            cameraTabButton.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        else{
            libraryTabButton.setBackgroundColor(Color.parseColor("#ffffff"));
            cameraTabButton.setBackgroundColor(Color.parseColor("#c4fffd"));
        }


    }

//    @Override
//    public void changed(String token) {
//        Log.e("", "Token changed");
//        videoViewModel.setToken(token);
//        streamingViewModel.setToken(token);
//    }

    @Override
    public void videoSelected() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainFragmentContainerView, new VideoDetailFragment(), "VideoDetailFragment")
                .setReorderingAllowed(true)
                .addToBackStack("VideoDetailFragment")
                .commit();
        videoDetailViewFlag = true;
    }

    @Override
    public void channelSelected(ChannelItem channelItem) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainFragmentContainerView, new StreamingFragment(), "StreamingFragment")
                .setReorderingAllowed(true)
                .addToBackStack("StreamingFragment")
                .commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("debug","onStart()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("debug","onRestart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("debug","onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("debug","onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("debug","onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("debug","onDestroy()");
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("key", "value");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        String value = savedInstanceState.getString("key");
        Log.d("Restore", value);
    }



}