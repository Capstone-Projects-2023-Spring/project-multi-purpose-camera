package com.example.layout_version.MainTab;

import androidx.annotation.NonNull;

enum State{
    IDLE,
    REQUESTED,
    LOADING,
    LOADED,
    FAILED,
    ERROR
}

public abstract class StateObservable {

    private State state;

    private StateChangeListener stateChangeListener;

    public StateObservable()
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
}
