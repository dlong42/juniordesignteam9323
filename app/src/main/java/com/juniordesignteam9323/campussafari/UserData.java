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
    private ArrayList<Achievement> achievements;

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
    public ArrayList<Achievement> getAchievements() {return this.achievements;}

    public void setPoints(int points) {this.points = points;}
    public void setLevel(int level) {this.level = level;}
    public void setNickname(String nickname) {this.nickname = nickname;}
    public void setAvatar(String avatar) {this.avatar = avatar;}
    public void setObLog(ArrayList<Wildlife> obLog) {this.obLog = obLog;}
    public void setAchievements(ArrayList<Achievement> achievements) {this.achievements = achievements;}

    public void setUpAchievements(){
        achievements.add(new Achievement("Campus Traveller", false, new boolean[4]));
        achievements.add(new Achievement("Tree Hugger", false, new boolean[10]));
        achievements.add(new Achievement("4.0 GPA", false, new boolean[1]));
        achievements.add(new Achievement("Taxa Driver", false, new boolean[8]));
        achievements.add(new Achievement("A for Effort", false, new boolean[1]));
        achievements.add(new Achievement("Hey, Come Back!", false, new boolean[1]));
        achievements.add(new Achievement("You're a Fun Guy (Ha Ha)", false, new boolean[1]));
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
        if(added.getTaxon().equals("Insecta") && !taxa.getNeeded()[0] ) {
            taxa.getNeeded()[0] = true;
            taxa.increaseCount();
        }
        if(added.getTaxon().equals("Aves") && !taxa.getNeeded()[1]) {
            taxa.getNeeded()[1] = true;
            taxa.increaseCount();
        }
        if(added.getTaxon().equals("Plantae") && !taxa.getNeeded()[2]) {
            taxa.getNeeded()[2] = true;
            taxa.increaseCount();
        }
        if(added.getTaxon().equals("Mammalia") && !taxa.getNeeded()[3]) {
            taxa.getNeeded()[3] = true;
            taxa.increaseCount();
        }
        if(added.getTaxon().equals("Arachnida") && !taxa.getNeeded()[4]) {
            taxa.getNeeded()[4] = true;
            taxa.increaseCount();
        }
        if(added.getTaxon().equals("Reptilia") && !taxa.getNeeded()[5]) {
            taxa.getNeeded()[5] = true;
            taxa.increaseCount();
        }
        if(added.getTaxon().equals("Animalia") && !taxa.getNeeded()[6]) {
            taxa.getNeeded()[6] = true;
            taxa.increaseCount();
        }
        if(added.getTaxon().equals("Fungi") && !taxa.getNeeded()[7]) {
            taxa.getNeeded()[7] = true;
            taxa.increaseCount();
        }
        if(taxa.getCount() == taxa.getNeeded().length + 1){
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
}
