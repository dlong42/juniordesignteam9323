package com.juniordesignteam9323.campussafari;

import java.io.Serializable;
import java.util.*;
import android.util.Log;

import com.google.common.util.concurrent.AbstractCheckedFuture;


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

    public void updateOblogElement(Wildlife wildlife) {
        for (int i = 0; i < obLog.size(); i++) {
            if (obLog.get(i).getLatitude() == wildlife.getLatitude()) {
                obLog.set(i, wildlife);

            }
        }
    }

    public boolean isAchieved(int i ) {
        return achievements.get(i).isAchieved();
    }
    public Achievement getAchievement(int i) {return achievements.get(i);}
    public boolean achievementBeenDisplayed(int i ) {return achievements.get(i).isDisplayed();}
    public void setDisplayed(int i, boolean displayed) {achievements.get(i).setDisplayed(displayed);}

    public ArrayList<Achievement> setUpAchievements(){
        ArrayList<Achievement> achievements = new ArrayList<Achievement>();
        achievements.add(new Achievement("Campus Traveller", false, new ArrayList<Check>(4), 4, "Collect this achievement by observing a wildlife in all four regions of campus.") );
        achievements.add(new Achievement("4.0 GPA", false, new ArrayList<Check>(1), 1, "Collect this achievement by observing the Albino Squirrel."));
        achievements.add(new Achievement("Taxa Driver", false, new ArrayList<Check>(8), 8 , "Collect his achievement by observing a wildlife in each of the 8 taxa."));
        achievements.add(new Achievement("A for Effort", false, new ArrayList<Check>(1), 1, "Collect this achievement by observing your first wildlife."));
        achievements.add(new Achievement("Hey, Come Back!", false, new ArrayList<Check>(1), 1, "Collect this achievement by going off campus and then returning."));
        achievements.add(new Achievement("You're a Fun Guy (Ha Ha)", false, new ArrayList<Check>(1), 1, "Collect this achievement by observing the only fungi on campus."));
        return achievements;
    }

    public void achievementCheck(Wildlife added) {
        //check for the campus traveller achievement, 0
        Log.d("achievement", "before campus traveller");
        this.traveller(added);
        Log.d("achievement", "after campus traveller");
        //check for the 4.0 achievement, 1
        Log.d("achievement", "before albino squirrel");
        if(added.getCommonName().equals("Albino Squirrel")) {
            achievements.get(1).increaseCount();
            achievements.get(1).setAchieved(true);
            achievements.get(1).setACheck(0, 1);
        }
        Log.d("achievement", "after albino squirrel");
        //check for the taxa driver achievement, 2
        Log.d("achievement", "before taxa driver");
        this.taxaDriver(added);
        Log.d("achievement", "after taxa driver");
        //check for the a for effort achievement, 3
        if(!achievements.get(3).isAchieved()){
            achievements.get(3).increaseCount();
            achievements.get(3).setAchieved(true);
            achievements.get(3).setACheck(0, 1);
        }
        //hey come back achievement is already checked for in Map Fragment, 4
        comeBack();
        //check for the fun guy achievement, 5
        if(added.getTaxon() == "Fungi") {
            achievements.get(5).increaseCount();
            achievements.get(5).setAchieved(true);
            achievements.get(5).setACheck(0, 1);
        }
    }

    public void traveller(Wildlife added) {
        //campus traveller achievement order
        //0 collected a wildlife in top left, 1 top right, 2 bottom right, 3 bottom left
        Achievement travel = achievements.get(0);
        double lat = added.getLatitude();
        double lon = added.getLongitude();
        Log.d("achievement", "lat: " + lat + " long: " + lon);
        if(lat >= 33.776 && lon <= -84.3995) {
            if (travel.checkCriteria(0) == 0) {
                travel.setACheck(0, 1);
                travel.increaseCount();
            }
        }
        else if(lat  >= 33.776 && lon >= -84.3995){
            if(travel.checkCriteria(1) == 0){
                travel.setACheck(1, 1);
                travel.increaseCount();
            }
        }
        else if(added.getLatitude() <= 33.776 && added.getLongitude() >= -84.3995){
            if(travel.checkCriteria(2) == 0){
                travel.setACheck(2, 1);
                travel.increaseCount();
            }
        }
        else{
            if(travel.checkCriteria(3) == 0){
                travel.setACheck(3, 1);
                travel.increaseCount();
            }
        }
        if(travel.getCount() == travel.getNeeded().size()){
            travel.setAchieved(true);
        }
        Log.d("achievement", "IN CAMPUS TRAVELLER " + travel.getCount());
    }
    public void taxaDriver(Wildlife added) {
        //taxa driver achievement order for the boolean array
        //0. insecta 1. aves 2. plantae 3. mammalia 4. arachnida 5. reptilia 6. animalia 7. fungi
        Achievement taxa = achievements.get(2);
        Log.d("achievement", ""+taxa.getNeeded().size());
        if(added.getTaxon().equals("Insecta") && taxa.checkCriteria(0) == 0 ) {
            taxa.setACheck(0, 1);
            taxa.increaseCount();
        }
        if(added.getTaxon().equals("Aves") && taxa.checkCriteria(1) == 0) {
            taxa.setACheck(1, 1);
            taxa.increaseCount();
        }
        if(added.getTaxon().equals("Plantae") && taxa.checkCriteria(2) == 0) {
            taxa.setACheck(2, 1);
            taxa.increaseCount();
        }
        if(added.getTaxon().equals("Mammalia") && taxa.checkCriteria(3) == 0) {
            taxa.setACheck(3, 1);
            taxa.increaseCount();
        }
        if(added.getTaxon().equals("Arachnida") && taxa.checkCriteria(4) == 0) {
            taxa.setACheck(4, 1);
            taxa.increaseCount();
        }
        if(added.getTaxon().equals("Reptilia") && taxa.checkCriteria(5) == 0) {
            taxa.setACheck(5, 1);
            taxa.increaseCount();
        }
        if(added.getTaxon().equals("Animalia") && taxa.checkCriteria(6) == 0) {
            taxa.setACheck(6, 1);
            taxa.increaseCount();
        }
        if(added.getTaxon().equals("Fungi") && taxa.checkCriteria(7) == 0) {
            taxa.setACheck(7, 1);
            taxa.increaseCount();
        }
        if(taxa.getCount() == taxa.getNeeded().size()){
            taxa.setAchieved(true);
        }
    }
    public void comeBack() {
        Achievement come = achievements.get(4);
        if(come.isAchieved() && come.checkCriteria(0) == 0) {
            come.setACheck(0, 1);
            come.increaseCount();
            Log.d("achievement", "increment come back "  + come.getCount() + " " + come.checkCriteria(0));
        }
        if(come.checkCriteria(0) == 0) {
            come.setAchieved(true);
            Log.d("achievement", "achieved come back achievement "  + come.getCount() + " " + come.checkCriteria(0));
        }
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
        achievementCheck(toAdd);
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
