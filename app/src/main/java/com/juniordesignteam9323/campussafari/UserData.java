package com.juniordesignteam9323.campussafari;

import java.io.Serializable;

public class UserData implements Serializable {
    private boolean admin;
    private String email;
    private int points;
    private int level;

    public UserData() {
    }

    public UserData(boolean admin, String email) {
        this.admin = admin;
        this.email = email;
        this.points = 0;
        this.level = 1;
    }

    public boolean getAdmin() {
        return this.admin;
    }
    public String getEmail() {return this.email;}
    public int getPoints() {return this.points;}
    public int getLevel() {return this.level;}

    public void setPoints(int points) {this.points = points;}
    public void setLevel(int level) {this.level = level;}
    
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
