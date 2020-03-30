package com.juniordesignteam9323.campussafari.ui.leaderboard;

import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class LeaderboardViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public LeaderboardViewModel() {
        mText = new MutableLiveData<String>();
        String s = "";
        mText.setValue(s);
    }

    public LiveData<String> getText() {
        return mText;
    }
}