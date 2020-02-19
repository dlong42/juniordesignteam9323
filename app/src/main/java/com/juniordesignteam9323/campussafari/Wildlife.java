package com.juniordesignteam9323.campussafari;
import java.util.*;

public class Wildlife {
    private String imageURl;
    private String name;
    private List<String> otherInformation;



    public String getImageURl() {
        return imageURl;
    }

    public void setImageURl(String imageURl) {
        this.imageURl = imageURl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getOtherInformation() {
        return otherInformation;
    }

    public void setOtherInformation(List<String> otherInformation) {
        this.otherInformation = otherInformation;
    }
}
