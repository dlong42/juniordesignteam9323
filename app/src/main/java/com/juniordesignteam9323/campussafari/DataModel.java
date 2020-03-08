package com.juniordesignteam9323.campussafari;


import android.graphics.drawable.Drawable;

public class DataModel {

    String nickname;
    String taxon;
    String scientificName;
    String commonName;
    int id_;
    Drawable image;


    //standardizes the information from Wildlife for the Custom Adapter, used in OblogActivity
    public DataModel(String nickname, String taxon, String scientificName, String commonName, int id_, Drawable image) {
        this.nickname = nickname;
        this.taxon = taxon;
        this.scientificName = scientificName;
        this.commonName = commonName;
        this.id_ = id_;
        this.image= image;
    }

    public String getNickname() {
        return nickname;
    }

    public String getTaxon() {
        return taxon;
    }

    public String getScientificName(){
        return scientificName;
    }

    public String getCommonName(){
        return commonName;
    }

    public Drawable getImage() {
        return image;
    }

    public int getId() {
        return id_;
    }
}
