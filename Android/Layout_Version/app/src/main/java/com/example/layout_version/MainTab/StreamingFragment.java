package com.example.layout_version.MainTab;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amazonaws.ivs.player.Cue;
import com.amazonaws.ivs.player.Player;
import com.amazonaws.ivs.player.PlayerException;
import com.amazonaws.ivs.player.PlayerView;
import com.amazonaws.ivs.player.Quality;
import com.example.layout_version.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StreamingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StreamingFragment extends Fragment {
    private Context context;
    private PlayerView streamingPlayerView;
    private Player streamingPlayer;

    public StreamingFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_streaming, container, false);
        context = layout.getContext();
        streamingPlayerView = layout.findViewById(R.id.streamingPlayerView);
        streamingPlayer = streamingPlayerView.getPlayer();
        streamingPlayer.addListener(new Player.Listener() {
            @Override
            public void onCue(@NonNull Cue cue) {

            }

            @Override
            public void onDurationChanged(long l) {

            }

            @Override
            public void onStateChanged(Player.State state) {
                switch (state) {
                    case BUFFERING:
                        // player is buffering
                        break;
                    case READY:
                        streamingPlayer.play();
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
        });
        return layout;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        streamingPlayer.load(Uri.parse("https://1958e2d97d88.us-east-1.playback.live-video.net/api/video/v1/us-east-1.052524269538.channel.HCBh4loJzOvw.m3u8"));
    }


}