package com.juniordesignteam9323.campussafari.ui.oblog;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OblogViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public OblogViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is log fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}