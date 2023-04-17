package com.example.layout_version.MainTab;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.layout_version.R;

public class VideoDetailFragment extends Fragment {


    private VideoViewModel videoViewModel;
    private TextView titleView;
    private VideoView videoView;
    private ImageButton playButton;
    private ImageButton stopButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_video_detail, container, false);

        titleView = layout.findViewById(R.id.videoTitleView);
        videoView = layout.findViewById(R.id.videoView);
        playButton = layout.findViewById(R.id.playButton);
        stopButton = layout.findViewById(R.id.stopButton);

        videoViewModel = new ViewModelProvider(requireActivity()).get(VideoViewModel.class);
        videoViewModel.getSelectedVideo().observe(getViewLifecycleOwner(), item -> {
            Log.e("Observer", item.getTitle());
            update(item);
        });


        return layout;
    }

    public void update(VideoItem video)
    {
        titleView.setText(video.getTitle());
    }
}