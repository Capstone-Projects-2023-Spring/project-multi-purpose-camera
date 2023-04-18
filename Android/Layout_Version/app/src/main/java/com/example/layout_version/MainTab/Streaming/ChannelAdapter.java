package com.example.layout_version.MainTab.Streaming;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amazonaws.ivs.player.Player;
import com.example.layout_version.R;

import java.util.List;
import java.util.function.Consumer;

public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.ViewHolder> {

    private final List<ChannelItem> localDataSet;
    private final Consumer<ChannelItem> clickEvent;
    private Context context;
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleView;
        private final TextView descriptionView;
        private final View view;
        private final TextView statusView;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            // Define click listener for the ViewHolder's View
            titleView = view.findViewById(R.id.streamingTitleView);
            descriptionView = view.findViewById(R.id.streamingDescriptionView);
            statusView = view.findViewById(R.id.deviceStatusView);
            view.setOnClickListener(v -> {
                Log.e("", "Channel Clicked");
            });

        }
        public View getView()
        {
            return view;
        }
        public TextView getTitleView() {
            return titleView;
        }
        public TextView getDescriptionView() {
            return descriptionView;
        }
        public TextView getStatusView(){
            return statusView;
        }

    }

    /**
     * Initialize the dataset of the Adapter
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView
     */
    public ChannelAdapter(List<ChannelItem> dataSet, Consumer<ChannelItem> clickEvent) {
        localDataSet = dataSet;
        this.clickEvent = clickEvent;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.streaming_item, viewGroup, false);
        context = viewGroup.getContext();
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTitleView().setText(localDataSet.get(position).getDeviceName());
        viewHolder.getDescriptionView().setText("Default description");
        viewHolder.getView().setOnClickListener(
                view -> clickEvent.accept(localDataSet.get(position))
        );
        Player player = Player.Factory.create(context);
        player.addListener(new StreamingPlayerListener(context, player, viewHolder.getStatusView(), false));
        player.load(Uri.parse(localDataSet.get(position).getPlaybackUrl()));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}