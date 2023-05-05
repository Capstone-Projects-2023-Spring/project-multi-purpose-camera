package com.example.layout_version.MainTab.Streaming;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.lifecycle.ViewModelProvider;

import com.amazonaws.ivs.player.Player;
import com.amazonaws.ivs.player.PlayerView;
import com.example.layout_version.Account.Account;
import com.example.layout_version.CameraShare.CameraConnectDialog;
import com.example.layout_version.CameraShare.CameraConnectFragment;
import com.example.layout_version.CameraShare.CameraShareDialog;
import com.example.layout_version.CameraShare.CameraShareFragment;
import com.example.layout_version.MainTab.State.StateFragment;
import com.example.layout_version.MainTab.Streaming.Recorder.Recorder;
import com.example.layout_version.MainTab.Streaming.Recorder.RecordingState;
import com.example.layout_version.MainTab.Streaming.Recorder.RecordingStateChangeListener;
import com.example.layout_version.Network.NetworkRequestManager;
import com.example.layout_version.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class StreamingFragment extends StateFragment<RecordingState> {
    private Context context;
    private StreamingViewModel streamingViewModel;
    private PlayerView streamingPlayerView;
    private Player streamingPlayer;
    private StreamingPlayerListener playerListener;
    private TextView deviceNameView;
    private TextView deviceStatusView;
    private ImageButton recordingButton;
    private ImageButton streamRefreshButton;

    private ImageButton optionButton;
    private ImageButton shareButton;
    private Recorder recorder;

    private boolean optionFlag;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_streaming, container, false);
        context = layout.getContext();

        streamingViewModel = new ViewModelProvider(requireActivity()).get(StreamingViewModel.class);
        streamingViewModel.getSelectedItem().observe(getViewLifecycleOwner(), this::update);

        streamingPlayerView = new PlayerView(layout.getContext());
        streamingPlayerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        streamingPlayerView.getControls().showControls(false);
        streamingPlayer = streamingPlayerView.getPlayer();

        FrameLayout playerFrameLayout = layout.findViewById(R.id.streamPlayerFrameView);
        playerFrameLayout.addView(streamingPlayerView);

        deviceNameView = layout.findViewById(R.id.deviceNameView);
        deviceStatusView = layout.findViewById(R.id.deviceStatusView);
        recordingButton = layout.findViewById(R.id.recordingButton);
        streamRefreshButton = layout.findViewById(R.id.streamRefreshButton);
        optionButton = layout.findViewById(R.id.optionButton);
        shareButton = layout.findViewById(R.id.shareButton);
        recorder = new Recorder(context, streamingViewModel);

        hideOptions();

        return layout;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setStateChangeListener(new RecordingStateChangeListener(context, recordingButton, streamingViewModel.getRecordingStateData().getValue()));
        streamingViewModel.getRecordingStateData().observe(getViewLifecycleOwner(), this::setState);
        recordingButton.setOnClickListener(view1 -> {
            RecordingState status = streamingViewModel.getRecordingStateData().getValue();
            if(status != RecordingState.BUFFERING)
            {
                if (status == RecordingState.STARTED)
                    recorder.stop();
                else if(status == RecordingState.STOPPED)
                    recorder.start();
                else if(status == RecordingState.FAILED)
                    recorder.askIsRecording(4);
            }
        });

        streamRefreshButton.setOnClickListener(view1 -> {
            recorder.askIsRecording(4);
            update(streamingViewModel.getSelectedItem().getValue());
        });

        optionButton.setOnClickListener(view1 -> {
            if(optionFlag)
                hideOptions();
            else
                showOptions();
        });

        shareButton.setOnClickListener(view1 -> {
            showShareDialog();
        });
    }

    public void update(@Nullable ChannelItem channel)
    {
        if(channel == null || channel.getPlaybackUrl() == null)
        {
            deviceStatusView.setBackground(AppCompatResources.getDrawable(context, R.drawable.unavailable_icon));
            deviceStatusView.setText(R.string.streaming_unavailable);
            deviceNameView.setText(R.string.streaming_unavailable);
            recorder.clear();
        }
        else{
            streamingPlayer.load(Uri.parse(channel.getPlaybackUrl()));
            playerListener = new StreamingPlayerListener(context, streamingPlayer, deviceStatusView, channel.getPlaybackUrl());
            streamingPlayer.addListener(playerListener);
            deviceNameView.setText(channel.getDeviceName());
            recorder.setDeviceId(channel.getDeviceId());
            recorder.setToken(Account.getInstance().getTokenData().getValue());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        streamingPlayer.removeListener(playerListener);
        streamingPlayer.release();
    }

    public void hideOptions()
    {
        optionFlag = false;
        optionButton.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.baseline_vert_options_24));
        recordingButton.setVisibility(View.INVISIBLE);
        shareButton.setVisibility(View.INVISIBLE);
    }

    public void showOptions()
    {
        optionFlag = true;
        optionButton.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.baseline_close_24));
        recordingButton.setVisibility(View.VISIBLE);
        shareButton.setVisibility(View.VISIBLE);
    }

    public void showShareDialog()
    {
        new CameraShareDialog().show(getChildFragmentManager(), "Tag");
    }


}
