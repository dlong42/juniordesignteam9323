package com.juniordesignteam9323.campussafari.ui.leaderboard;

import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.juniordesignteam9323.campussafari.UserData;

import java.util.List;

public class LeaderboardViewModel extends ViewModel implements ViewModelProvider.Factory{

    private MutableLiveData<String> mText;
    private UserData userData;

    public LeaderboardViewModel(UserData userData) {
        this.userData = userData;
        mText = new MutableLiveData<String>();
        String s = "";
        mText.setValue(s);
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new LeaderboardViewModel(userData);
    }

    public LiveData<String> getText() {
        return mText;
    }
}