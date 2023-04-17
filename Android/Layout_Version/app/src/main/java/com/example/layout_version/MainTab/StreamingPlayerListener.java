package com.example.layout_version.MainTab;

import androidx.annotation.NonNull;

import com.amazonaws.ivs.player.Cue;
import com.amazonaws.ivs.player.Player;
import com.amazonaws.ivs.player.PlayerException;
import com.amazonaws.ivs.player.Quality;

public class StreamingPlayerListener extends Player.Listener {

    private Player player;

    public StreamingPlayerListener(Player player)
    {
        this.player = player;
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
                break;
            case READY:
                player.play();
                break;
            case IDLE:
                break;
            case PLAYING:
                // playback started
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
