package com.example.layout_version.MainTab.State;

public interface StateChangeListener<E extends State> {
    void onStateChanged(E state);
}
