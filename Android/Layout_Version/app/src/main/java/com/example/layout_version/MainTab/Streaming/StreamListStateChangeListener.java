package com.example.layout_version.MainTab.Streaming;

import androidx.annotation.NonNull;

import com.example.layout_version.MainTab.State;
import com.example.layout_version.MainTab.StateObservableFragment;

public class StreamListStateChangeListener implements StateObservableFragment.StateChangeListener {

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
            case FAILED:
                break;
            case ERROR:
                break;
        }
    }
}
