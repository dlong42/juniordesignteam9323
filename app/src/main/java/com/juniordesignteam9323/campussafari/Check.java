package com.juniordesignteam9323.campussafari;
import java.io.Serializable;

public class Check implements Serializable{
    private int check;
    public Check(){
        this.check = 0;
    }
    public Check(int check){
        this.check = check;
    }
    public int getCheck() {return this.check;}
    public void setCheck(int check){this.check = check;}
}
