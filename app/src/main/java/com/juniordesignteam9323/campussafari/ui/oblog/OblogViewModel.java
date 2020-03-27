package com.juniordesignteam9323.campussafari.ui.oblog;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.juniordesignteam9323.campussafari.UserData;

public class OblogViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public OblogViewModel() {
        mText = new MutableLiveData<>();

        int numCaught = 0;
        for (int i = 0; i < UserData.getObLog().size(); i++){
            if (UserData.getObLog().get(i).getCaught()){
                numCaught++;
            }
        }
        if (numCaught == 0){
            mText.setValue("You have no observations in your log.");
        }

    }

    public LiveData<String> getText() {
        return mText;
    }
}