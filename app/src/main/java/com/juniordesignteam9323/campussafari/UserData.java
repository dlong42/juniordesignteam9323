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
        achievements.add(new Achievement("4.0 GPA", false, new ArrayList<Boolean>(1)));
        achievements.add(new Achievement("Taxa Driver", false, new ArrayList<Boolean>(8)));
        achievements.add(new Achievement("A for Effort", false, new ArrayList<Boolean>(1)));
        achievements.add(new Achievement("Hey, Come Back!", false, new ArrayList<Boolean>(1)));
        achievements.add(new Achievement("You're a Fun Guy (Ha Ha)", false, new ArrayList<Boolean>(1)));
        return achievements;
    }

    public void achievementCheck(Wildlife added) {
        //check for the campus traveller achievement, 0
        this.traveller(added);
        //check for the 4.0 achievement, 1
        if(added.getCommonName().equals("Albino Squirrel")) {
            achievements.get(1).increaseCount();
            achievements.get(1).setAchieved(true);
        }
        //check for the taxa driver achievement, 2
        this.taxaDriver(added);
        //check for the a for effort achievement, 3
        if(!achievements.get(3).isAchieved()){
            achievements.get(3).increaseCount();
            achievements.get(3).setAchieved(true);
        }
        //hey come back achievement is already checked for in Map Fragment, 4
        //check for the fun guy achievement, 5
        if(added.getTaxon() == "Fungi") {
            achievements.get(5).increaseCount();
            achievements.get(5).setAchieved(true);
        }
    }
    public void traveller(Wildlife added) {
        //campus traveller achievement order
        //0 collected a wildife in top left, 1 top right, 2 bottom right, 3 bottom left
        Achievement travel = achievements.get(0);
        if(added.getLatitude() >= 33.776 && added.getLongitude() <= -84.3995){
            if(!travel.getNeeded().get(0)){
                ArrayList<Boolean> temp = travel.getNeeded();
                temp.set(0, true);
                travel.setNeeded(temp);
                travel.increaseCount();
            }
        } else if(added.getLatitude() >= 33.776 && added.getLongitude() >= -84.3995){
            if(!travel.getNeeded().get(1)){
                ArrayList<Boolean> temp = travel.getNeeded();
                temp.set(1, true);
                travel.setNeeded(temp);
                travel.increaseCount();
            }
        } else if(added.getLatitude() <= 33.776 && added.getLongitude() >= -84.3995){
            if(!travel.getNeeded().get(2)){
                ArrayList<Boolean> temp = travel.getNeeded();
                temp.set(2, true);
                travel.setNeeded(temp);
                travel.increaseCount();
            }
        } else{
            if(!travel.getNeeded().get(3)){
                ArrayList<Boolean> temp = travel.getNeeded();
                temp.set(3, true);
                travel.setNeeded(temp);
                travel.increaseCount();
            }
        }
        if(travel.getCount() == travel.getNeeded().size()){
            travel.setAchieved(true);
        }
    }
    public void taxaDriver(Wildlife added) {
        //taxa driver achievement order for the boolean array
        //0. insecta 1. aves 2. plantae 3. mammalia 4. arachnida 5. reptilia 6. animalia 7. fungi
        Achievement taxa = achievements.get(2);
        Log.d("achievement", ""+taxa.getNeeded().size());
        if(added.getTaxon().equals("Insecta") && !taxa.getNeeded().get(0) ) {
            ArrayList<Boolean> temp = taxa.getNeeded();
            temp.set(0, true);
            taxa.setNeeded(temp);
            taxa.increaseCount();
        }
        if(added.getTaxon().equals("Aves") && !taxa.getNeeded().get(1)) {
            ArrayList<Boolean> temp = taxa.getNeeded();
            temp.set(1, true);
            taxa.setNeeded(temp);
            taxa.increaseCount();
        }
        if(added.getTaxon().equals("Plantae") && !taxa.getNeeded().get(2)) {
            ArrayList<Boolean> temp = taxa.getNeeded();
            temp.set(2, true);
            taxa.setNeeded(temp);
            taxa.increaseCount();
        }
        if(added.getTaxon().equals("Mammalia") && !taxa.getNeeded().get(3)) {
            ArrayList<Boolean> temp = taxa.getNeeded();
            temp.set(3, true);
            taxa.setNeeded(temp);
            taxa.increaseCount();
        }
        if(added.getTaxon().equals("Arachnida") && !taxa.getNeeded().get(4)) {
            ArrayList<Boolean> temp = taxa.getNeeded();
            temp.set(4, true);
            taxa.setNeeded(temp);
            taxa.increaseCount();
        }
        if(added.getTaxon().equals("Reptilia") && !taxa.getNeeded().get(5)) {
            ArrayList<Boolean> temp = taxa.getNeeded();
            temp.set(5, true);
            taxa.setNeeded(temp);
            taxa.increaseCount();
        }
        if(added.getTaxon().equals("Animalia") && !taxa.getNeeded().get(6)) {
            ArrayList<Boolean> temp = taxa.getNeeded();
            temp.set(6, true);
            taxa.setNeeded(temp);
            taxa.increaseCount();
        }
        if(added.getTaxon().equals("Fungi") && !taxa.getNeeded().get(7)) {
            ArrayList<Boolean> temp = taxa.getNeeded();
            temp.set(7, true);
            taxa.setNeeded(temp);
            taxa.increaseCount();
        }
        if(taxa.getCount() == taxa.getNeeded().size()){
            taxa.setAchieved(true);
        }
    }
    public void comeBack() {
        Log.d("achievement", "achieved come back achievement");
        Achievement come = achievements.get(4);
        come.setAchieved(true);
        come.increaseCount();
    }
    public ArrayList<Achievement> toDisplayAchievements() {
        int front = 0;
        int back = achievements.size() - 1;
        ArrayList<Achievement> toReturn = new ArrayList<Achievement>(achievements.size());
        for(Achievement a : achievements){
            if(a.isAchieved()) {
                toReturn.set(front, a);
                front++;
            } else {
                toReturn.set(back, a);
                back--;
            }
        }
        return toReturn;
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
