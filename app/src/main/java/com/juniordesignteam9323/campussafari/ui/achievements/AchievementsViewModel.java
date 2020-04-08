package com.juniordesignteam9323.campussafari.ui.achievements;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.juniordesignteam9323.campussafari.UserData;

public class AchievementsViewModel extends ViewModel implements ViewModelProvider.Factory {

    private UserData userData;

    public AchievementsViewModel(UserData userData) {
        this.userData = userData;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AchievementsViewModel(userData);
    }
}