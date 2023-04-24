package com.example.layout_version.MainTab;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public abstract  class StateViewModel<E> extends ViewModel {
    private final MutableLiveData<Integer> updateFlag;

    private final MutableLiveData<E> selectedData;

    private final List<E> dataList;

    private final MutableLiveData<State> stateData;

    public StateViewModel(){
        updateFlag = new MutableLiveData<>(0);
        selectedData = new MutableLiveData<>();
        dataList = new ArrayList<>();
        stateData = new MutableLiveData<>(State.IDLE);
    }

    public MutableLiveData<E> getSelectedItem() {
        return selectedData;
    }

    public void setSelectedItem(E item)
    {
        selectedData.setValue(item);
    }

    public List<E> getDataList()
    {
        return dataList;
    }

    public void setDataList(List<E> videos)
    {
        this.dataList.clear();
        this.dataList.addAll(videos);
        updatedSignal();
    }

    public MutableLiveData<State> getStateData() {
        return stateData;
    }

    public void setStateData(State state)
    {
        stateData.setValue(state);
    }

    public MutableLiveData<Integer> getUpdateFlag()
    {
        return updateFlag;
    }

    public void updatedSignal()
    {
        updateFlag.setValue(updateFlag.getValue() + 1);
    }

    public void clearUpdate()
    {
        updatedSignal();
    }
}
