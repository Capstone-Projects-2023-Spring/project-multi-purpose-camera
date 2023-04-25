package com.example.layout_version.MainTab.State;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;

import com.example.layout_version.R;

public class NetworkStateChangeListener implements StateChangeListener<NetworkState>{
    private Context context;
    private TextView statusView;
    public NetworkStateChangeListener(Context context, TextView statusView, NetworkState networkState) {
        this.context = context;
        this.statusView  = statusView;
        onStateChanged(networkState);
    }

    public void onStateChanged(@NonNull NetworkState networkState) {
        switch (networkState)
        {

            case IDLE:
                statusView.setBackground(AppCompatResources.getDrawable(context, R.drawable.unavailable_icon));
                statusView.setText(R.string.streaming_unavailable);
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
