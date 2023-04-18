package com.example.layout_version.MainTab.Streaming;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.layout_version.MainActivity;
import com.example.layout_version.MainTab.Library.VideoAdapter;
import com.example.layout_version.MainTab.Library.VideoItem;
import com.example.layout_version.MainTab.Library.VideoViewModel;
import com.example.layout_version.R;

import java.util.function.Consumer;

public class StreamingListFragment extends Fragment {

    private StreamingViewModel streamingViewModel;
    private RecyclerView recyclerView;

    public static StreamingListFragment newInstance() {
        return new StreamingListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        streamingViewModel = new ViewModelProvider(requireActivity()).get(StreamingViewModel.class);

        View layout = inflater.inflate(R.layout.fragment_streaming_list, container, false);
        recyclerView = layout.findViewById(R.id.streamingRecyclerView);

        return layout;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}