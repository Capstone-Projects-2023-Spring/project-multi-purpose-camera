package com.example.layout_version.MainTab.Library;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;

import com.example.layout_version.MainTab.State;
import com.example.layout_version.MainTab.StateObservableFragment;
import com.example.layout_version.R;

public class LibraryStateChangeListener implements StateObservableFragment.StateChangeListener {
    private final TextView statusView;
    private final Context context;
    public LibraryStateChangeListener(Context context, TextView statusView, State state) {
        this.context = context;
        this.statusView  = statusView;
        onStateChanged(state);
    }

    @Override
    public void onStateChanged(@NonNull State state) {
        switch(state)
        {
            case IDLE:
                break;
            case REQUESTED:
                statusView.setBackground(AppCompatResources.getDrawable(context, R.drawable.offline_icon));
                statusView.setText("Requested");
                break;
            case LOADING:
                statusView.setBackground(AppCompatResources.getDrawable(context, R.drawable.buffering_icon));
                statusView.setText("Loading");
                break;
            case LOADED:
                statusView.setBackground(AppCompatResources.getDrawable(context, R.drawable.success_icon));
                statusView.setText("Loaded");
                break;
            case RETRY:
                statusView.setBackground(AppCompatResources.getDrawable(context, R.drawable.offline_icon));
                statusView.setText("Retrying");
                break;
            case FAILED:
                statusView.setBackground(AppCompatResources.getDrawable(context, R.drawable.online_icon));
                statusView.setText("Failed");
                break;
            case ERROR:
                break;
        }
    }
}
