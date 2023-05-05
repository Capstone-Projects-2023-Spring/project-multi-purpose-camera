package com.example.layout_version.MainTab.Streaming.Recorder;

import com.example.layout_version.MainTab.State.State;

public enum RecordingState implements State {
    STOPPED,
    STARTED,
    BUFFERING,
    RETRY,
    FAILED
}
