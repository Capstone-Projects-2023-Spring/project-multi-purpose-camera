package com.example.layout_version.MainTab;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;

import com.example.layout_version.R;

public class StateChangeListener {
    private Context context;
    private TextView statusView;
    public StateChangeListener(Context context, TextView statusView, State state) {
        this.context = context;
        this.statusView  = statusView;
        onStateChanged(state);
    }

    public void onStateChanged(@NonNull State state) {
        switch (state)
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