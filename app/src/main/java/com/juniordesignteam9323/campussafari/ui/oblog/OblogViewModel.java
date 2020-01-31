package com.juniordesignteam9323.campussafari.ui.oblog;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OblogViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public OblogViewModel() {
        mText = new MutableLiveData<>();
<<<<<<< Updated upstream

        mText.setValue("This is observation log fragment");
=======
        mText.setValue("This is Observation Log fragment");
>>>>>>> Stashed changes
    }

    public LiveData<String> getText() {
        return mText;
    }
}