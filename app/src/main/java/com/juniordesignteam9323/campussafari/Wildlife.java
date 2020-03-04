package com.juniordesignteam9323.campussafari;
import java.util.*;

public class Wildlife {
    private String taxon;
    private String scientificName;
    private String commonName;
    private String latitude;
    private String longitude;
    private String imageURl;
    private String level;
    private String points;

    public Wildlife(String commonName) {
        this.commonName = commonName;
    }

    public String getTaxon() {return taxon;}
    public String getScientificName() {return scientificName;}
    public String getCommonName() {return commonName;}
    public String getLatitude() {return latitude;}
    public String getLongitude() {return longitude;}
    public String getImageURl() {return imageURl;}
    public String getLevel() {return level;}
    public String getPoints() {return points;}


    public void setTaxon(String taxon) {this.taxon = taxon;}
    public void setScientificName(String scientificName) {this.scientificName = scientificName;}
    public void setCommonName(String commonName) {this.commonName = commonName;}
    public void setLatitude(String latitude) {this.latitude = latitude;}
    public void setLongitude(String longitude) {this.longitude = longitude;}
    public void setImageURl(String imageURl) {this.imageURl = imageURl;}
    public void setLevel(String level) {this.level = level;}
    public void setPoints(String points) {this.points = points;}

}
