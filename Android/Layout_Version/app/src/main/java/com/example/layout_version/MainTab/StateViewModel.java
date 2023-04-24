package com.example.layout_version.MainTab;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.layout_version.MainTab.Library.VideoItem;

import java.util.ArrayList;
import java.util.List;

public abstract  class StateViewModel<E> extends ViewModel {
    private final MutableLiveData<Integer> requestedFlag;
    private final MutableLiveData<Integer> loadedFlag;

    private final MutableLiveData<Integer> updateFlag;

    private final MutableLiveData<E> selectedData;

    private final List<E> dataList;

    public StateViewModel()
    {
        requestedFlag = new MutableLiveData<>(0);
        loadedFlag = new MutableLiveData<>(0);
        updateFlag = new MutableLiveData<>(0);
        selectedData = new MutableLiveData<>();
        dataList = new ArrayList<>();
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

    public MutableLiveData<Integer> getRequestedFlag()
    {
        return requestedFlag;
    }

    public void requestedSignal()
    {
        requestedFlag.setValue(requestedFlag.getValue() + 1);
    }

    public MutableLiveData<Integer> getLoadedFlag()
    {
        return loadedFlag;
    }

    public void loadedSignal()
    {
        loadedFlag.setValue(loadedFlag.getValue() + 1);
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
