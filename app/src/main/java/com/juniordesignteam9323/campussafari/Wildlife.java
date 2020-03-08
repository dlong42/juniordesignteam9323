package com.juniordesignteam9323.campussafari;
import java.util.*;

public class Wildlife {
    private String taxon;
    private String scientificName;
    private String commonName;
    private String level;
    private String points;
    private String url;

    public Wildlife(String commonName) {
        this.commonName = commonName;
    }

    public String getTaxon() {return taxon;}
    public String getScientificName() {return scientificName;}
    public String getCommonName() {return commonName;}
    public String getLevel() {return level;}
    public String getPoints() {return points;}
    public String getUrl() {return url;}


    public void setTaxon(String taxon) {this.taxon = taxon;}
    public void setScientificName(String scientificName) {this.scientificName = scientificName;}
    public void setCommonName(String commonName) {this.commonName = commonName;}
    public void setLevel(String level) {this.level = level;}
    public void setPoints(String points) {this.points = points;}
    public void setUrl(String url) {this.url = url;}

}
