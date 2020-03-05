package com.juniordesignteam9323.campussafari.ui.oblog;

import androidx.appcompat.app.AppCompatActivity;

import com.juniordesignteam9323.campussafari.R;
import com.juniordesignteam9323.campussafari.Wildlife;

public class WildlifeActivity extends AppCompatActivity {
    String image_url;
    String common_name;
    String scientific_name;
    String taxon;
    String wildlife_nickname;
    String fun_fact;
    String level;
    String points_worth;


    public void onCreate(Wildlife wildlife) {
        setContentView(R.layout.activity_wildlife);

        this.image_url = wildlife.getImageURl();
        this.common_name = wildlife.getCommonName();
        this.scientific_name = wildlife.getScientificName();
        this.taxon = wildlife.getTaxon();
        this.level = wildlife.getLevel();
        this.points_worth = wildlife.getPoints();

    }

    // This method will eventually be completed to edit a wildlife's nickname
    public void changeWildlifeNickname() {
        // wildlife.setNickname(user input);
        // this.wildlife_nickname = wildlife.getNickname();
    }

}
