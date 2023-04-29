package com.example.layout_version.MainTab.Streaming;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.amazonaws.ivs.player.Player;
import com.amazonaws.ivs.player.PlayerView;
import com.example.layout_version.Account.Account;
import com.example.layout_version.MainTab.Library.VideoItem;
import com.example.layout_version.Network.NetworkRequestManager;
import com.example.layout_version.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.ViewHolder> {

    private final List<ChannelItem> localDataSet;
    private final Consumer<ChannelItem> clickEvent;
    private Context context;
    private final List<Player> players;
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleView;
        private final TextView descriptionView;
        private final View view;
        private final TextView statusView;
        private final PlayerView playerView;
        private final ImageView optionButton;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            // Define click listener for the ViewHolder's View
            titleView = view.findViewById(R.id.streamingTitleView);
            descriptionView = view.findViewById(R.id.streamingDescriptionView);
            statusView = view.findViewById(R.id.deviceStatusView);
            optionButton = view.findViewById(R.id.delete_item);

            FrameLayout playerFrameLayout = view.findViewById(R.id.playerFrameLayout);
            playerView = new PlayerView(view.getContext());
            playerView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            playerView.getControls().showControls(false);
            playerFrameLayout.addView(playerView);

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
        public PlayerView getPlayerView(){
            return playerView;
        }
        public ImageView getOptionButton(){
            return optionButton;
        }

    }

    private void showAlertDialog(@NonNull View v, int position)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        NetworkRequestManager nrm = new NetworkRequestManager(v.getContext());

        builder.setTitle("Delete?");
        builder.setMessage("Are you sure you want to delete?");

        builder.setPositiveButton("Yes", (dialog, i) -> {
            Toast.makeText(v.getContext(), "Deleting item", Toast.LENGTH_SHORT).show();
            String token = Account.getInstance().getTokenData().getValue();
            if(token == null)
                ;
            else{
                JSONObject jsonObject = new JSONObject(
                        Map.of("token", token, "device_id", localDataSet.get(position).getDeviceId()));

                v.setVisibility(View.INVISIBLE);
                nrm.Post(R.string.hardware_delete_endpoint, jsonObject,
                        json -> {
                            removeAt(position);
                            Toast.makeText(v.getContext(), "Item removed", Toast.LENGTH_SHORT).show();
                        },
                        json -> {
                            Toast.makeText(v.getContext(), "Failed to remove item", Toast.LENGTH_SHORT).show();
                            v.setVisibility(View.VISIBLE);
                        });
            }

            dialog.dismiss();
        });
        builder.setNegativeButton("No", (dialog, i) -> dialog.dismiss());
        builder.create().show();
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
        players = new ArrayList<>();
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

        Player player = viewHolder.getPlayerView().getPlayer();
        players.add(player);
        if(localDataSet.get(position).getPlaybackUrl() != null)
        {
            player.addListener(new StreamingPlayerListener(context, player, viewHolder.getStatusView(), localDataSet.get(position).getPlaybackUrl(), false));
            player.load(Uri.parse(localDataSet.get(position).getPlaybackUrl()));
            viewHolder.getView().setOnClickListener(
                    view -> clickEvent.accept(localDataSet.get(position))
            );
        }else{
            viewHolder.getStatusView().setBackground(AppCompatResources.getDrawable(context, R.drawable.unavailable_icon));
            viewHolder.getStatusView().setText(R.string.streaming_unavailable);
        }

        viewHolder.getOptionButton().setOnClickListener(v->{
            showAlertDialog(v, position);
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public void checkPlayers(){
        Log.e("Streaming Adapter Check", players.size() + "");
        for(Player player: players)
        {
            Log.e("State", player.getState() + "");
        }
    }

    public void removeAt(int position) {
        localDataSet.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, localDataSet.size());
    }
}