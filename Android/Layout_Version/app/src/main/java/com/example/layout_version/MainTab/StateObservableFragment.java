package com.example.layout_version.MainTab;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


public abstract class StateObservableFragment extends Fragment {

    private State state;

    private StateChangeListener stateChangeListener;

    public StateObservableFragment()
    {

    }

    public void setStateChangeListener(@NonNull StateChangeListener stateChangeListener) {
        this.stateChangeListener = stateChangeListener;
    }

    public void setState(@NonNull State state) {
        if(stateChangeListener != null && this.state != state)
            stateChangeListener.onStateChanged(state);
        this.state = state;
    }

    public interface StateChangeListener {

        void onStateChanged(@NonNull State state);
    }
}
