package com.example.layout_version.MainTab.Streaming;

import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.layout_version.MainTab.State;
import com.example.layout_version.MainTab.StateObservableFragment;

public class StreamListStateChangeListener implements StateObservableFragment.StateChangeListener {
    private TextView textView;
    public StreamListStateChangeListener(TextView textView) {
        this.textView  = textView;
    }

    @Override
    public void onStateChanged(@NonNull State state) {
        switch (state)
        {

            case IDLE:
                break;
            case REQUESTED:
                break;
            case LOADING:
                break;
            case LOADED:
                break;
            case RETRY:
                break;
            case FAILED:
                break;
            case ERROR:
                break;
        }
    }
}
