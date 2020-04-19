package com.juniordesignteam9323.campussafari.ui.leaderboard;

import java.util.ArrayList;

public class LeaderboardDataModel {
    private String leaderName;
    private int points;

    //standardizes the information from Achievements for the Custom Adapter, used in LeaderboardFragment
    public LeaderboardDataModel(String leaderName, int points) {
        this.leaderName = leaderName;
        this.points = points;
    }

    public String getName() {
        return leaderName;
    }

    public int getPoints(){
        return points;
    }



}
