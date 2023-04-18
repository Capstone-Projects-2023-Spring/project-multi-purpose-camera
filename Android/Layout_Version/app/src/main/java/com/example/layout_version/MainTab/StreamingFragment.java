package com.example.layout_version.MainTab;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amazonaws.ivs.player.Player;
import com.amazonaws.ivs.player.PlayerView;
import com.example.layout_version.R;

public class StreamingFragment extends Fragment{
    private Context context;
    private PlayerView streamingPlayerView;
    private Player streamingPlayer;
    private StreamingPlayerListener playerListener;
    private TextView deviceNameView;
    private TextView deviceStatusView;

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

        playerListener = new StreamingPlayerListener(streamingPlayer);
        streamingPlayer.addListener(playerListener);

        deviceNameView = layout.findViewById(R.id.deviceNameView);
        deviceStatusView = layout.findViewById(R.id.deviceStatusView);

        return layout;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        streamingPlayer.load(Uri.parse("https://1958e2d97d88.us-east-1.playback.live-video.net/api/video/v1/us-east-1.052524269538.channel.HCBh4loJzOvw.m3u8"));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        streamingPlayer.removeListener(playerListener);
        streamingPlayer.release();
    }
}