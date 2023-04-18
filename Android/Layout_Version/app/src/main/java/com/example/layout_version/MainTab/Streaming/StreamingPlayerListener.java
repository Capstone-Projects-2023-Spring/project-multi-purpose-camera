package com.example.layout_version.MainTab.Streaming;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;

import com.amazonaws.ivs.player.Cue;
import com.amazonaws.ivs.player.Player;
import com.amazonaws.ivs.player.PlayerException;
import com.amazonaws.ivs.player.Quality;
import com.example.layout_version.R;

public class StreamingPlayerListener extends Player.Listener {

    private final Context context;
    private final Player player;
    private final TextView deviceStatusView;

    public StreamingPlayerListener(Context context, Player player, TextView deviceStatusView)
    {
        this.context = context;
        this.player = player;
        this.deviceStatusView = deviceStatusView;
    }

    @Override
    public void onCue(@NonNull Cue cue) {

    }

    @Override
    public void onDurationChanged(long l) {

    }

    @Override
    public void onStateChanged(@NonNull Player.State state) {
        switch (state) {
            case BUFFERING:
                // player is buffering
                deviceStatusView.setBackground(AppCompatResources.getDrawable(context, R.drawable.buffering_icon));
                deviceStatusView.setText(R.string.streaming_buffering);
                break;
            case READY:
                deviceStatusView.setBackground(AppCompatResources.getDrawable(context, R.drawable.online_icon));
                deviceStatusView.setText(R.string.streaming_online);
                player.play();
                break;
            case IDLE:
                deviceStatusView.setBackground(AppCompatResources.getDrawable(context, R.drawable.offline_icon));
                deviceStatusView.setText(R.string.streaming_idle);
                break;
            case PLAYING:
                // playback started
                deviceStatusView.setBackground(AppCompatResources.getDrawable(context, R.drawable.online_icon));
                deviceStatusView.setText(R.string.streaming_streaming);
                break;
        }
    }

    @Override
    public void onError(@NonNull PlayerException e) {

    }

    @Override
    public void onRebuffering() {

    }

    @Override
    public void onSeekCompleted(long l) {

    }

    @Override
    public void onVideoSizeChanged(int i, int i1) {

    }

    @Override
    public void onQualityChanged(@NonNull Quality quality) {

    }
}
