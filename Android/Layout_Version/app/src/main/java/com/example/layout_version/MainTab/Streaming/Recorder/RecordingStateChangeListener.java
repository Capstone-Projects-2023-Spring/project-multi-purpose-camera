package com.example.layout_version.MainTab.Streaming.Recorder;

import android.content.Context;
import android.util.Log;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;

import com.example.layout_version.MainTab.State.StateChangeListener;
import com.example.layout_version.R;

public class RecordingStateChangeListener implements StateChangeListener<RecordingState> {
    private final Context context;
    private final ImageButton statusView;

    public RecordingStateChangeListener(
            @NonNull Context context,
            @NonNull ImageButton statusView,
            @NonNull RecordingState recordingState) {
        this.context = context;
        this.statusView  = statusView;
        onStateChanged(recordingState);
    }
    @Override
    public void onStateChanged(@NonNull RecordingState state) {
        switch (state) {
            case STOPPED:
                statusView.setBackground(AppCompatResources.getDrawable(context, R.drawable.offline_icon));
                statusView.setImageResource(R.drawable.baseline_videocam_off_24);
                break;
            case STARTED:
                statusView.setBackground(AppCompatResources.getDrawable(context, R.drawable.online_icon));
                statusView.setImageResource(R.drawable.baseline_videocam_24);
                break;
            case BUFFERING:
                statusView.setBackground(AppCompatResources.getDrawable(context, R.drawable.buffering_icon));
                statusView.setImageResource(R.drawable.baseline_videocam_off_24);
                break;
            case RETRY:
                break;
            case FAILED:
                statusView.setBackground(AppCompatResources.getDrawable(context, R.drawable.unavailable_icon));
                statusView.setImageResource(R.drawable.baseline_videocam_off_24);
                break;
        }
    }
}
