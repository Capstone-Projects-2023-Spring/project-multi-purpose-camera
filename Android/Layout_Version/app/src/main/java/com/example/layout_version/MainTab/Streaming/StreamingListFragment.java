package com.example.layout_version.MainTab.Streaming;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.layout_version.Network.NetworkRequestManager;
import com.example.layout_version.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.function.Consumer;

public class StreamingListFragment extends Fragment {

    private Context context;
    private StreamingViewModel streamingViewModel;
    private RecyclerView streamingRecyclerView;

    public static StreamingListFragment newInstance() {
        return new StreamingListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        streamingViewModel = new ViewModelProvider(requireActivity()).get(StreamingViewModel.class);

        View layout = inflater.inflate(R.layout.fragment_streaming_list, container, false);
        streamingRecyclerView = layout.findViewById(R.id.streamingRecyclerView);
        context = layout.getContext();
        return layout;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Consumer<ChannelItem> clickEvent = channelItem -> {
            streamingViewModel.setSelectedChannel(channelItem);
            if(requireActivity() instanceof StreamingListFragmentInterface)
                ((StreamingListFragmentInterface)requireActivity()).channelSelected(channelItem);
            Log.e("Channel Item", channelItem.getDeviceName());
        };

        ChannelAdapter adapter = new ChannelAdapter(streamingViewModel.getChannelList(), clickEvent);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        streamingRecyclerView.setAdapter(adapter);
        streamingRecyclerView.setLayoutManager(layoutManager);

        streamingViewModel.getUpdateFlag().observe(getViewLifecycleOwner(), updateFlag -> {
            adapter.notifyDataSetChanged();
        });


    }
}