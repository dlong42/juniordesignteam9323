package com.juniordesignteam9323.campussafari;
import java.io.Serializable;

/**
 * A Wildlife class to represent wildlife within the app.
 * Serializeable so it can be stored in Firebase.
 */
public class Wildlife implements Serializable {
    private String commonName;
    private String scientificName;
    private String taxon;
    private String level;
    private String points;
    private String image_url;
    private String nickname;
    private double latitude;
    private double longitude;
    private int id;
    private boolean caught;
    private String funFact;
    private String citation;

    public Wildlife() {
        this.commonName = null;
        this.caught = false;
    }
    public Wildlife(String commonName) {
        this.commonName = commonName;
        this.caught = false;
    }

    public Wildlife(String commonName, String scientificName, String taxon, String level, String points, String image_url, double latitude, double longitude, int id){
        this.commonName = commonName;
        this.scientificName = scientificName;
        this.taxon = taxon;
        this.level = level;
        this.points = points;
        this.image_url = image_url;
        this.latitude = latitude;
        this.longitude = longitude;
        this.id = id;
        this.caught = false;
    }

    public Wildlife(String commonName, String scientificName, String taxon, String level, String points, String image_url, double latitude, double longitude, int id, String funFact, String citation){
        this.commonName = commonName;
        this.scientificName = scientificName;
        this.taxon = taxon;
        this.level = level;
        this.points = points;
        this.image_url = image_url;
        this.latitude = latitude;
        this.longitude = longitude;
        this.id = id;
        this.caught = false;
        this.funFact = funFact;
        this.citation = citation;
    }

    public String getTaxon() {return taxon;}
    public String getScientificName() {return scientificName;}
    public String getCommonName() {return commonName;}
    public String getLevel() {return level;}
    public String getPoints() {return points;}
    public String getImage_url() {return image_url;}
    public double getLatitude() {return latitude;}
    public double getLongitude() {return longitude;}
    public int getId() {return id;}
    public boolean getCaught() {return caught;}
    public String getNickname() { return nickname; }
    public String getFunFact() {return funFact;}
    public String getCitation() {return citation;}

    public void setTaxon(String taxon) {this.taxon = taxon;}
    public void setScientificName(String scientificName) {this.scientificName = scientificName;}
    public void setCommonName(String commonName) {this.commonName = commonName;}
    public void setLevelRec(String level) {this.level = level;}
    public void setPoints(String points) {this.points = points;}
    public void setImage_url(String image_url) {this.image_url = image_url;}
    public void setLatitude(double latitude) {this.latitude = latitude;}
    public void setLongitude(double longitude) {this.longitude = longitude;}
    public void setId(int id) {this.id = id;}
    public void setCaught(boolean caught) {this.caught = caught;}
    public void setNickname(String nickname) { this.nickname = nickname; }
    public void setFunFact(String funFact) {this.funFact = funFact;}
    public void setCitation(String citation) {this.citation = citation;}

    //changes the caught variable to true, and returns true indicating a successful catch
    //return false if the wildlife has been caught before
    public boolean catchWildlife(){
        if(this.caught){
            return false;
        } else {
            this.caught = true;
            return true;
        }
    }
    public boolean equals(Wildlife other) {
        return this.id == other.id;
    }
}
