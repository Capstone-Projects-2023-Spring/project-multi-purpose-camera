package com.example.layout_version;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.layout_version.Account.Account;
import com.example.layout_version.Account.Account_Page;
import com.example.layout_version.Account.Account_Page_Profile;
import com.example.layout_version.Library.Library;
import com.example.layout_version.MainTab.LibraryFragment;
import com.example.layout_version.MainTab.VideoState;
import com.example.layout_version.MainTab.VideoViewModel;

//import org.opencv.highgui.HighGui;


public class MainActivity extends AppCompatActivity{

    private Fragment libraryFragment;
    private VideoViewModel videoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Thread.sleep(2000);
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

        ImageView btn = findViewById(R.id.settings);
        ImageView account = findViewById(R.id.account);
        Button lib = findViewById(R.id.library);
        Button camera = findViewById(R.id.view);

        btn.setOnClickListener(view -> {
            Intent intent = new Intent (MainActivity.this,Settings.class);
            startActivity(intent);
        });

        account.setOnClickListener(view -> {
            Account account1 = Account.getInstance();
            Intent intent;
            if(account1.isSignedIn())
                intent = new Intent (MainActivity.this, Account_Page_Profile.class);
            else
                intent = new Intent (MainActivity.this, Account_Page.class);

            startActivity(intent);
        });


        videoViewModel = new ViewModelProvider(this).get(VideoViewModel.class);
        if(savedInstanceState == null) {
            Log.d("", "New state");
            libraryFragment =LibraryFragment.newInstance("New", "Instance");

            videoViewModel.setVidState(new VideoState("TitleSamplke" ,"Description"));
            Log.e("Live Data Created ", "Live Data Hre");
        }
        else
            Log.d("", "Npot New state");

        videoViewModel = new ViewModelProvider(this).get(VideoViewModel.class);

        lib.setOnClickListener(view -> {
            camera.setBackgroundColor(Color.parseColor("#ffffff"));
            lib.setBackgroundColor(Color.parseColor("#c4fffd"));
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainFragmentContainerView, libraryFragment, "LibraryFragment")
                    .addToBackStack(null)
                    .commit();
        });

        camera.setOnClickListener(view -> {
            LibraryFragment fragment = (LibraryFragment)getSupportFragmentManager().findFragmentByTag("LibraryFragment");
            if (fragment != null && fragment.isVisible()) {
                getSupportFragmentManager().popBackStack();
            }

            lib.setBackgroundColor(Color.parseColor("#ffffff"));
            camera.setBackgroundColor(Color.parseColor("#c4fffd"));
        });
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

}