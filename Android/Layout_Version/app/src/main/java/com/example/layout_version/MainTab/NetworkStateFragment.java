package com.example.layout_version.MainTab;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


public abstract class NetworkStateFragment extends Fragment {

    private NetworkState networkState;

    private NetworkStateChangeListener networkStateChangeListener;

    public void setStateChangeListener(@NonNull NetworkStateChangeListener networkStateChangeListener) {
        this.networkStateChangeListener = networkStateChangeListener;
    }

    public void setState(@NonNull NetworkState networkState) {
        if(networkStateChangeListener != null && this.networkState != networkState)
            networkStateChangeListener.onStateChanged(networkState);
        this.networkState = networkState;
    }
}
