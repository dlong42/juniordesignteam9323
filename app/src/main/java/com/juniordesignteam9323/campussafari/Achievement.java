package com.juniordesignteam9323.campussafari;
import java.util.*;
import java.io.Serializable;

public class Achievement implements Serializable{
    private String name;
    private boolean achieved;
    private ArrayList<Check> needed;
    private int count;

    public Achievement() {
        this.name = null;
        this.achieved = false;
        this.count = 0;
        this.needed = new ArrayList<Check>();
    }
    public Achievement(String name) {
        this.name = name;
        this.achieved = false;
        this.needed = new ArrayList<Check>();
        this.count = 0;
    }
    public Achievement(String name, boolean achieved, ArrayList<Check> needed) {
        this.name = name;
        this.achieved = achieved;
        this.needed = needed;
        Collections.fill(needed, new Check());
        this.count = 0;
    }
    public Achievement(String name, boolean achieved, ArrayList<Check> needed, int sz) {
        this.name = name;
        this.achieved = achieved;
        this.needed = needed;
        for(int i = 0; i < sz; i++) {
            needed.add(new Check());
        }
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
    public ArrayList<Check> getNeeded() {return needed;}
    public void setNeeded(ArrayList<Check> needed) {
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

    public int checkCriteria(int a) {
        return needed.get(a).getCheck();
    }
    public void setACheck(int a, int b) {
        Check temp = needed.get(a);
        temp.setCheck(b);
        needed.set(a, temp);
    }

}
