package com.juniordesignteam9323.campussafari.ui.oblog;

import com.juniordesignteam9323.campussafari.UserData;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class OblogViewModel extends ViewModel implements ViewModelProvider.Factory {

    private MutableLiveData<String> mText;
    private UserData userData;

    public OblogViewModel(UserData userData) {
        this.userData = userData;
        mText = new MutableLiveData<>();

        int numCaught = 0;
        for (int i = 0; i < userData.getObLog().size(); i++){
            if (userData.getObLog().get(i).getCaught()){
                numCaught++;
            }
        }
        if (numCaught == 0){
            mText.setValue("You have no observations in your log.");
        }

    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new OblogViewModel(userData);
    }

    public LiveData<String> getText() {
        return mText;
    }
}