package com.juniordesignteam9323.campussafari;

import java.io.Serializable;
import java.util.*;
import android.util.Log;


public class UserData implements Serializable {
    private boolean admin;
    private String email;
    private int points;
    private int level;
    private String nickname;
    private String avatar;
    private ArrayList<Wildlife> obLog;
    private ArrayList<Achievement> achievements;

    public UserData() {
        this.obLog = new ArrayList<Wildlife>();
        this.level = 1;
        this.achievements = setUpAchievements();
    }

    public UserData(boolean admin, String email) {
        this.admin = admin;
        this.email = email;
        this.obLog = new ArrayList<Wildlife>();
        this.level = 1;
        this.achievements = setUpAchievements();
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
    public ArrayList<Achievement> getAchievements() {return this.achievements;}

    public void setPoints(int points) {this.points = points;}
    public void setLevel(int level) {this.level = level;}
    public void setNickname(String nickname) {this.nickname = nickname;}
    public void setAvatar(String avatar) {this.avatar = avatar;}
    public void setObLog(ArrayList<Wildlife> obLog) {this.obLog = obLog;}
    public void setAchievements(ArrayList<Achievement> achievements) {this.achievements = achievements;}

    public ArrayList<Achievement> setUpAchievements(){
        ArrayList<Achievement> achievements = new ArrayList<Achievement>();
        achievements.add(new Achievement("Campus Traveller", false, new ArrayList<Boolean>(4)));
        achievements.add(new Achievement("Tree Hugger", false, new ArrayList<Boolean>(10)));
        achievements.add(new Achievement("4.0 GPA", false, new ArrayList<Boolean>(1)));
        achievements.add(new Achievement("Taxa Driver", false, new ArrayList<Boolean>(8)));
        achievements.add(new Achievement("A for Effort", false, new ArrayList<Boolean>(1)));
        achievements.add(new Achievement("Hey, Come Back!", false, new ArrayList<Boolean>(1)));
        achievements.add(new Achievement("You're a Fun Guy (Ha Ha)", false, new ArrayList<Boolean>(1)));
        return achievements;
    }

    public void achievementCheck(Wildlife added) {
        //check for the campus traveller achievement, 0
        //check for the tree hugger achievement, 1
        //check for the 4.0 achievement, 2
        if(added.getCommonName().equals("Albino Squirrel")) {
            achievements.get(2).increaseCount();
            achievements.get(2).setAchieved(true);
        }
        //check for the taxa driver achievement, 3
        this.taxaDriver(added);
        //check for the a for effort achievement, 4
        if(!achievements.get(4).isAchieved()){
            achievements.get(4).increaseCount();
            achievements.get(4).setAchieved(true);
        }
        //check for the hey come back achievement, 5
        //check for the fun guy achievement, 6
        if(added.getTaxon() == "Fungi") {
            achievements.get(6).increaseCount();
            achievements.get(6).setAchieved(true);
        }
    }

    public void taxaDriver(Wildlife added) {
        //taxa driver achievement order for the boolean array
        //0. insecta 1. aves 2. plantae 3. mammalia 4. arachnida 5. reptilia 6. animalia 7. fungi
        Achievement taxa = achievements.get(3);
        Log.d("achievement", ""+taxa.getNeeded().size());
        if(added.getTaxon().equals("Insecta") && !taxa.getNeeded().get(0) ) {
            taxa.getNeeded().set(0, true);
            taxa.increaseCount();
        }
        if(added.getTaxon().equals("Aves") && !taxa.getNeeded().get(1)) {
            taxa.getNeeded().set(1, true);
            taxa.increaseCount();
        }
        if(added.getTaxon().equals("Plantae") && !taxa.getNeeded().get(2)) {
            taxa.getNeeded().set(2, true);
            taxa.increaseCount();
        }
        if(added.getTaxon().equals("Mammalia") && !taxa.getNeeded().get(3)) {
            taxa.getNeeded().set(3, true);
            taxa.increaseCount();
        }
        if(added.getTaxon().equals("Arachnida") && !taxa.getNeeded().get(4)) {
            taxa.getNeeded().set(4, true);
            taxa.increaseCount();
        }
        if(added.getTaxon().equals("Reptilia") && !taxa.getNeeded().get(5)) {
            taxa.getNeeded().set(5, true);
            taxa.increaseCount();
        }
        if(added.getTaxon().equals("Animalia") && !taxa.getNeeded().get(6)) {
            taxa.getNeeded().set(6, true);
            taxa.increaseCount();
        }
        if(added.getTaxon().equals("Fungi") && !taxa.getNeeded().get(7)) {
            taxa.getNeeded().set(7, true);
            taxa.increaseCount();
        }
        if(taxa.getCount() == taxa.getNeeded().size()){
            taxa.setAchieved(true);
        }
    }
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
