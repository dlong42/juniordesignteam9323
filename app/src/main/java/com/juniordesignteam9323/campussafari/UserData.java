package com.juniordesignteam9323.campussafari;

import java.io.Serializable;
import java.util.*;


public class UserData implements Serializable {
    private boolean admin;
    private String email;
    private int points;
    private int level;
    private String nickname;
    private String avatar;
    private ArrayList<Wildlife> obLog;

    public UserData() {
        this.obLog = new ArrayList<Wildlife>();
    }

    public UserData(boolean admin, String email) {
        this.admin = admin;
        this.email = email;
        this.obLog = new ArrayList<Wildlife>();
    }

    public boolean getAdmin() {
        return this.admin;
    }
    public String getEmail() {return this.email;}
    public int getPoints() {return this.points;}
    public int getLevel() {return this.level;}
    public String getAvatar() {return this.avatar;}
    public String getNickname() {return this.nickname;}
    public ArrayList<Wildlife> getObLog() {return obLog;}


    public void setPoints(int points) {this.points = points;}
    public void setLevel(int level) {this.level = level;}
    public void setNickname(String nickname) {this.nickname = nickname;}
    public void setAvatar(String avatar) {this.avatar = avatar;}
    public void setObLog(ArrayList<Wildlife> obLog) {this.obLog = obLog;}

    public void addToObLog(Wildlife toAdd) {
        if(this.obLog == null) {
            this.obLog = new ArrayList<Wildlife>();
        }

        this.obLog.add(toAdd);
    }

    public String getObLogString(){
        String toReturn = "\n";
        if(this.obLog != null){
            for(Wildlife w : this.obLog){
                toReturn += "\n" + w.getCommonName() + " " + w.getId() + " " + w.getCaught();
            }
            return toReturn;
        }
        return "empty observation log";
    }
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof UserData)) {
            return false;
        }

        UserData other = (UserData) object;
        return (this.admin == other.admin && this.email.equals(other.email));
    }

    public boolean isCaught(Wildlife w) {
        for(Wildlife wild : obLog) {
            if(w.equals(wild)) {
                return true;
            }
        }
        return false;
    }

    public boolean updatePoints(int newPoints) {
        this.points += newPoints;

        if (this.points >= 300 && this.level < 2) {
            this.level = 2;
            return true;
        } else if (this.points >= 900 && this.level < 3) {
            this.level = 3;
            return true;
        } else if (this.points >= 1800 && this.level < 4) {
            this.level = 4;
            return true;
        } else if (this.points >= 3600 && this.level == 5) {
            this.level = 5;
            return true;
        }

        return false;
    }

    public static int levelThreshold(int level) {
        switch(level) {
            case 1:
                return 300;
            case 2:
                return 900;
            case 3:
                return 1800;
            case 4:
                return 3600;
            case 5:
                return 8000;
            default:
                return 0;
        }


    }
}
