package com.example.layout_version.MainTab.State;

public enum NetworkState implements State{
    IDLE,
    REQUESTED,
    LOADING,
    LOADED,
    RETRY,
    FAILED,
    ERROR
}
