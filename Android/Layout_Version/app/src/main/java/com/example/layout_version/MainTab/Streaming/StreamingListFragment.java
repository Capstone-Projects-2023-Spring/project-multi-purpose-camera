package com.example.layout_version.MainTab.Streaming;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.layout_version.Account.Account;
import com.example.layout_version.MainTab.StateObservableFragment;
import com.example.layout_version.R;

import java.util.function.Consumer;

public class StreamingListFragment  extends StateObservableFragment {

    private Context context;
    private StreamingViewModel streamingViewModel;
    private RecyclerView streamingRecyclerView;

    private TextView streamStatusTextView;
    private ImageButton refreshButton;

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
        streamStatusTextView = layout.findViewById(R.id.streamStatusTextView);
        refreshButton = layout.findViewById(R.id.streamRefreshButton);
        return layout;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Consumer<ChannelItem> clickEvent = channelItem -> {
            streamingViewModel.setSelectedItem(channelItem);
            if(requireActivity() instanceof StreamingListFragmentInterface)
                ((StreamingListFragmentInterface)requireActivity()).channelSelected(channelItem);
            Log.e("Channel Item", channelItem.getDeviceName());
        };

        ChannelAdapter adapter = new ChannelAdapter(streamingViewModel.getDataList(), clickEvent);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        streamingRecyclerView.setAdapter(adapter);
        streamingRecyclerView.setLayoutManager(layoutManager);

        streamingViewModel.getUpdateFlag().observe(getViewLifecycleOwner(), updateFlag -> {
            adapter.notifyDataSetChanged();
        });

        setStateChangeListener(new StreamListStateChangeListener(context, streamStatusTextView, streamingViewModel.getStateData().getValue()));
        streamingViewModel.getStateData().observe(getViewLifecycleOwner(), this::setState);
        refreshButton.setOnClickListener(view1 -> {
            StreamingListFragmentInterface.loadData(context, streamingViewModel, Account.getInstance().getTokenData().getValue(), 4);
        });
    }
}