package com.example.layout_version.SenderStream;

import android.support.annotation.NonNull;
import android.util.Log;

import com.amazonaws.ivs.broadcast.BroadcastException;
import com.amazonaws.ivs.broadcast.BroadcastSession;

public class LivestreamBroadcastListener extends BroadcastSession.Listener{
    @Override
    public void onStateChanged(@NonNull BroadcastSession.State state) {
        Log.d("TAG", "State=" + state);
    }

    @Override
    public void onError(@NonNull BroadcastException exception) {
        Log.e("TAG", "Exception: " + exception);
    }
}
