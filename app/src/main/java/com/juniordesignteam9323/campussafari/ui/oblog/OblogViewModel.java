package com.juniordesignteam9323.campussafari.ui.oblog;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.juniordesignteam9323.campussafari.UserData;

public class OblogViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public OblogViewModel() {
        mText = new MutableLiveData<>();

        if (UserData.getObLog().size() == 0){
            mText.setValue("You have no observations in your log.");
        }
    }

    public LiveData<String> getText() {
        return mText;
    }
}