package com.example.layout_version.SenderStream;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;

import com.amazonaws.ivs.broadcast.BroadcastException;
import com.amazonaws.ivs.broadcast.BroadcastSession;
import com.example.layout_version.R;

public class LivestreamBroadcastListener extends BroadcastSession.Listener{
    private Context context;
    private TextView liveStatusTextView;

    public LivestreamBroadcastListener(Context context, TextView liveStatusTextView)
    {
        this.context = context;
        this.liveStatusTextView = liveStatusTextView;
    }

    @Override
    public void onStateChanged(@NonNull BroadcastSession.State state) {
        switch(state)
        {
            case INVALID:
                liveStatusTextView.setBackground(AppCompatResources.getDrawable(context, R.drawable.unavailable_icon));
                liveStatusTextView.setText(R.string.streaming_unavailable);
                break;
            case DISCONNECTED:
                liveStatusTextView.setBackground(AppCompatResources.getDrawable(context, R.drawable.offline_icon));
                liveStatusTextView.setText(R.string.live_disconnected);
                break;
            case CONNECTING:
                liveStatusTextView.setBackground(AppCompatResources.getDrawable(context, R.drawable.buffering_icon));
                liveStatusTextView.setText(R.string.live_connecting);
                break;
            case CONNECTED:
                liveStatusTextView.setBackground(AppCompatResources.getDrawable(context, R.drawable.online_icon));
                liveStatusTextView.setText(R.string.live_connected);
                break;
            case ERROR:
                liveStatusTextView.setBackground(AppCompatResources.getDrawable(context, R.drawable.offline_icon));
                liveStatusTextView.setText(R.string.streaming_offline);
                break;
        }
    }

    @Override
    public void onError(@NonNull BroadcastException exception) {
        Log.e("TAG", "Exception: " + exception);
    }
}
