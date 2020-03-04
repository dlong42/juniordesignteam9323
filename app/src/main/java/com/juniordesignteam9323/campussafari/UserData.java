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
    public ArrayList<Wildlife> getObLog() {return this.obLog;}

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

}
