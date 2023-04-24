package com.example.layout_version.MainTab.Streaming;

import android.content.Context;
import android.widget.ImageButton;

import androidx.appcompat.content.res.AppCompatResources;

import com.example.layout_version.MainTab.State.StateChangeListener;
import com.example.layout_version.R;

public class RecordingStateChangeListener implements StateChangeListener<RecordingState> {
    private Context context;
    private ImageButton statusView;

    public RecordingStateChangeListener(Context context, ImageButton statusView, RecordingState recordingState) {
        this.context = context;
        this.statusView  = statusView;
        onStateChanged(recordingState);
    }
    @Override
    public void onStateChanged(RecordingState state) {
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
                statusView.setImageResource(R.drawable.baseline_videocam_24);
                break;
            case RETRY:
                break;
            case FAILED:
                break;
        }
    }
}
