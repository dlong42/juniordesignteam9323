package com.juniordesignteam9323.campussafari.ui.oblog;


import android.graphics.drawable.Drawable;

import com.juniordesignteam9323.campussafari.Wildlife;

public class DataModel {

    String nickname;
    String taxon;
    String scientificName;
    String commonName;
    int id_;
    Drawable image;
    Wildlife type;


    //standardizes the information from Wildlife for the Custom Adapter, used in OblogFragment
    public DataModel(String nickname, String taxon, String scientificName, String commonName, int id_, Drawable image, Wildlife type) {
        this.nickname = nickname;
        this.taxon = taxon;
        this.scientificName = scientificName;
        this.commonName = commonName;
        this.id_ = id_;
        this.image= image;
        this.type = type;
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

    public Wildlife getType() {return type;}
}
