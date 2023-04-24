package com.example.layout_version.MainTab;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public abstract  class NetworkStateViewModel<E> extends ViewModel {
    private final MutableLiveData<Integer> updateFlag;

    private final MutableLiveData<E> selectedData;

    private final List<E> dataList;

    private final MutableLiveData<NetworkState> stateData;

    public NetworkStateViewModel(){
        updateFlag = new MutableLiveData<>(0);
        selectedData = new MutableLiveData<>();
        dataList = new ArrayList<>();
        stateData = new MutableLiveData<>(NetworkState.IDLE);
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

    public MutableLiveData<NetworkState> getStateData() {
        return stateData;
    }

    public void setStateData(NetworkState networkState)
    {
        stateData.setValue(networkState);
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
