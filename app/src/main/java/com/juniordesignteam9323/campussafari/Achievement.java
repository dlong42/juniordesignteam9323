package com.juniordesignteam9323.campussafari;
import java.util.*;
import java.io.Serializable;

public class Achievement implements Serializable{
    private String name;
    private boolean achieved;
    private boolean needed[];
    private int count;

    public Achievement() {
        this.name = null;
        this.achieved = false;
        this.count = 0;
    }
    public Achievement(String name) {
        this.name = name;
        this.achieved = false;
        this.needed = null;
        this.count = 0;
    }
    public Achievement(String name, boolean achieved, boolean[] needed) {
        this.name = name;
        this.achieved = achieved;
        this.needed = needed;
        this.count = 0;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public boolean isAchieved() {
        return achieved;
    }
    public void setAchieved(boolean achieved) {
        this.achieved = achieved;
    }
    public boolean[] getNeeded() {
        return needed;
    }
    public void setNeeded(boolean[] needed) {
        this.needed = needed;
    }
    public int getCount(){
        return count;
    }
    public void setCount(int count){
        this.count = count;
    }
    public void increaseCount() {
        this.count++;
    }
}
