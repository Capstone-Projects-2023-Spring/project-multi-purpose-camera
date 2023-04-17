package com.example.layout_version;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Test_Video_Page extends AppCompatActivity{
    private PlayerView playerView;
    private SimpleExoPlayer exoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testing_video_page);

        playerView = findViewById(R.id.test_video_page_player_view);
        initializePlayer();
    }

    private byte[] getVideoBytesFromYourServer(){
        AsyncTask mytask = new AsyncTask(){

            @Override
            protected Object doInBackground(Object[] objects) {

                return null;
            }
        };
        //return resourceToByteArray(this, R.raw.pain);
    }

    private void initializePlayer() {
        exoPlayer = new SimpleExoPlayer.Builder(this).build();
        playerView.setPlayer(exoPlayer);

        // Get your byte array here, for example:
        byte[] videoBytes = getVideoBytesFromYourServer();

        // Save the byte array to a temporary file
        File tempFile = null;
        try {
            tempFile = saveByteArrayToFile(videoBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (tempFile != null) {
            // Set up the MediaSource using the file's URI
            Uri uri = Uri.fromFile(tempFile);
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, "ExoPlayer");
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            MediaSource mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory, extractorsFactory)
                    .createMediaSource(MediaItem.fromUri(uri));

            exoPlayer.setMediaSource(mediaSource);
            exoPlayer.prepare();
            exoPlayer.setPlayWhenReady(true);
        } else {
            // Handle the error, for example, show a toast or a message
        }
    }

    private File saveByteArrayToFile(byte[] bytes) throws IOException {
        File outputFile = File.createTempFile("video_", ".mp4", getCacheDir());
        FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
        fileOutputStream.write(bytes);
        fileOutputStream.close();
        return outputFile;
    }

    public static byte[] resourceToByteArray(Context context, int resourceId) {
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = null;
        try {
            Resources resources = context.getResources();
            inputStream = resources.openRawResource(resourceId);
            outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer, 0, buffer.length)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            return outputStream.toByteArray();
        } catch (Resources.NotFoundException | IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (exoPlayer != null) {
            exoPlayer.setPlayWhenReady(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (exoPlayer != null) {
            exoPlayer.setPlayWhenReady(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (exoPlayer != null) {
            exoPlayer.release();
        }
    }
}
