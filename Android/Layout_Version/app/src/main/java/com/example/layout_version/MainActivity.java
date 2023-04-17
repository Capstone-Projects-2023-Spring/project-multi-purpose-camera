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
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.layout_version.Account.Account;
import com.example.layout_version.Account.Account_Page;
import com.example.layout_version.Account.Account_Page_Profile;
import com.example.layout_version.Account.NetworkRequestManager;
import com.example.layout_version.Account.TokenChangeInterface;
import com.example.layout_version.MainTab.LibraryFragment;
import com.example.layout_version.MainTab.LibraryFragmentInterface;
import com.example.layout_version.MainTab.VideoDetailFragment;
import com.example.layout_version.MainTab.VideoItem;
import com.example.layout_version.MainTab.VideoViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//import org.opencv.highgui.HighGui;


public class MainActivity extends AppCompatActivity implements TokenChangeInterface, LibraryFragmentInterface {

    private Fragment libraryFragment;
    private VideoViewModel videoViewModel;
    private Account account;
    private Button libraryTabButton;
    private Button cameraTabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videoViewModel = new ViewModelProvider(this).get(VideoViewModel.class);
        account = Account.getInstance(this);

        try {
            Thread.sleep(1000);
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
        ImageView accountImageView = findViewById(R.id.account);
        libraryTabButton = findViewById(R.id.library);
        cameraTabButton = findViewById(R.id.view);

        btn.setOnClickListener(view -> {
            Intent intent = new Intent (MainActivity.this,Settings.class);
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
            libraryFragment =LibraryFragment.newInstance(true);
        }
        else
            Log.d("", "Npot New state");

        videoViewModel = new ViewModelProvider(this).get(VideoViewModel.class);

        libraryTabButton.setOnClickListener(view -> {
            LibraryFragment fragment = (LibraryFragment)getSupportFragmentManager().findFragmentByTag("LibraryFragment");
            if (fragment != null && fragment.isVisible()) {
                return;
            }
            VideoDetailFragment videoDetailFragment = (VideoDetailFragment)getSupportFragmentManager().findFragmentByTag("VideoDetailFragment");
            if (videoDetailFragment != null && videoDetailFragment.isVisible()) {
                getSupportFragmentManager().popBackStack();
                return;
            }
            cameraTabButton.setBackgroundColor(Color.parseColor("#ffffff"));
            libraryTabButton.setBackgroundColor(Color.parseColor("#c4fffd"));
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainFragmentContainerView, libraryFragment, "LibraryFragment")
                    .addToBackStack("LibraryFragment")
                    .commit();
        });

        cameraTabButton.setOnClickListener(view -> {
            LibraryFragment fragment = (LibraryFragment)getSupportFragmentManager().findFragmentByTag("LibraryFragment");
            if (fragment != null && fragment.isVisible()) {
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

    @Override
    public void changed(String token) {
        Log.e("", "Token changed");
        videoViewModel.setToken(token);
        if(token == null)
        {
            videoViewModel.videoListUpdated();
            return;
        }
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("token", token);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        NetworkRequestManager nrm = new NetworkRequestManager(this);
        nrm.Post(R.string.file_all_endpoint, jsonObject,
                json -> {
                    Log.e("", "Load video list");
                    JSONArray fileArray;
                    try {
                        fileArray = json.getJSONArray("files");
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    List<VideoItem> videos = IntStream.range(0,fileArray.length())
                            .mapToObj(i -> {
                                try {
                                    JSONObject item = fileArray.getJSONObject(i);
                                    return new VideoItem(item.get("file_name").toString(), item.get("timestamp").toString(), item.getString("url"));
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            })
                            .collect(Collectors.toList());

                    videoViewModel.setVideoList(videos);

                    videoViewModel.videoListUpdated();
                },
                json -> {});
    }

    @Override
    public void videoSelected() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainFragmentContainerView, new VideoDetailFragment(), "VideoDetailFragment")
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();
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