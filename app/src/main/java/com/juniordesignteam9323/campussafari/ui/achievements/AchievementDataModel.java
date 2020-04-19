package com.juniordesignteam9323.campussafari.ui.achievements;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.juniordesignteam9323.campussafari.Achievement;
import com.juniordesignteam9323.campussafari.Check;
import com.juniordesignteam9323.campussafari.R;

import java.util.ArrayList;

public class AchievementDataModel {
    private String name;
    private boolean achieved;
    private ArrayList<Check> needed;
    private int count;
    private String description;
    private Drawable trophy;


    //standardizes the information from Achievements for the Custom Adapter, used in AchievementsFragment
    public AchievementDataModel(String name, Boolean achieved, ArrayList<Check> needed, int count, String description, Drawable trophy) {
        this.name = name;
        this.achieved = achieved;
        this.needed = needed;
        this.count = count;
        this.trophy = trophy;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public boolean isAchieved(){
        return achieved;
    }

    public ArrayList<Check> getNeeded(){
        return needed;
    }

    public int getCount(){
        return count;
    }

    public String getDescription() {return description;}

    public Drawable getTrophy() {
        return trophy;
    }

}
