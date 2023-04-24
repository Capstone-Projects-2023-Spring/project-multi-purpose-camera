package com.example.layout_version.MainTab.Library;

import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.layout_version.MainTab.State;
import com.example.layout_version.MainTab.StateObservableFragment;

public class LibraryStateChangeListener implements StateObservableFragment.StateChangeListener {
    private TextView textView;
    public LibraryStateChangeListener(TextView textView) {
        this.textView  = textView;
    }

    @Override
    public void onStateChanged(@NonNull State state) {
        switch(state)
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
