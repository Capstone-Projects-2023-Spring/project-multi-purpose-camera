package com.example.layout_version.MainTab.State;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


public abstract class StateFragment<E extends State> extends Fragment {

    private E state;

    private StateChangeListener<E> stateChangeListener;

    public void setStateChangeListener(@NonNull StateChangeListener<E> stateChangeListener) {
        this.stateChangeListener = stateChangeListener;
    }

    public void setState(@NonNull E state) {
        if(stateChangeListener != null && this.state != state)
            stateChangeListener.onStateChanged(state);
        this.state = state;
    }
}
