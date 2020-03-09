package com.juniordesignteam9323.campussafari.ui.oblog;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.juniordesignteam9323.campussafari.R;
import com.juniordesignteam9323.campussafari.Wildlife;

import java.io.InputStream;
import java.net.URL;

public class WildlifeActivity extends AppCompatActivity {
    String image_url;
    String common_name;
    String scientific_name;
    String taxon;
    String wildlife_nickname;
    String fun_fact;
    String level;
    String points_worth;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wildlife);

        Wildlife wildlife = (Wildlife) getIntent().getSerializableExtra("WILDLIFE");

        this.image_url = wildlife.getImage_url();
        this.common_name = wildlife.getCommonName();
        this.scientific_name = wildlife.getScientificName();
        this.taxon = wildlife.getTaxon();
        this.level = wildlife.getLevel();
        this.points_worth = wildlife.getPoints();
        ImageView imgView =(ImageView)findViewById(R.id.wildlife_pic);
        Drawable drawable = LoadImageFromWebOperations(image_url);
        imgView.setImageDrawable(drawable);

        TextView common = findViewById(R.id.wildlife_common);
        common.setText(getTextC(wildlife.getCommonName()));

        TextView scientific = findViewById(R.id.wildlife_scientific);
        scientific.setText(getTextS(wildlife.getScientificName()));
    }

    public String getTextC(String text) {return "Common Name: " + text;}
    public String getTextS(String text) {return "Scientific Name: " + text;}

    private Drawable LoadImageFromWebOperations(String url)
    {
        try{
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        }catch (Exception e) {
            System.out.println("Exc="+e);
            return null;
        }
    }

}
