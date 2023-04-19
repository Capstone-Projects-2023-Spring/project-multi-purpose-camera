package com.example.layout_version.MainTab.Streaming;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amazonaws.ivs.player.Player;
import com.amazonaws.ivs.player.PlayerView;
import com.example.layout_version.MainTab.Library.VideoItem;
import com.example.layout_version.MainTab.Library.VideoViewModel;
import com.example.layout_version.R;

public class StreamingFragment extends Fragment{
    private Context context;
    private StreamingViewModel streamingViewModel;
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
        deviceNameView = layout.findViewById(R.id.deviceNameView);
        deviceStatusView = layout.findViewById(R.id.deviceStatusView);

        streamingPlayer = streamingPlayerView.getPlayer();
        streamingPlayerView.getControls().showControls(false);

        return layout;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        streamingViewModel = new ViewModelProvider(requireActivity()).get(StreamingViewModel.class);
        streamingViewModel.getSelectedChannel().observe(getViewLifecycleOwner(), item -> {
            Log.e("Observer", item.getDeviceName());
            update(item);
        });

    }

    public void update(ChannelItem channel)
    {
        deviceNameView.setText(channel.getDeviceName());
        if(channel.getPlaybackUrl() != null)
        {
            streamingPlayer.load(Uri.parse(channel.getPlaybackUrl()));
            playerListener = new StreamingPlayerListener(context, streamingPlayer, deviceStatusView, channel.getPlaybackUrl());
            streamingPlayer.addListener(playerListener);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        playerListener.shutdown();
        streamingPlayer.removeListener(playerListener);
        streamingPlayer.release();
    }


}