package com.juniordesignteam9323.campussafari.ui.oblog;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.juniordesignteam9323.campussafari.MainActivity;
import com.juniordesignteam9323.campussafari.R;
import com.juniordesignteam9323.campussafari.UserData;
import com.juniordesignteam9323.campussafari.Wildlife;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class WildlifeActivity extends AppCompatActivity implements View.OnClickListener{
    String image_url;
    String common_name;
    String scientific_name;
    String taxon;
    String wildlife_nickname;
    String fun_fact;
    String level;
    String points_worth;
    String newName;
    String funFact;
    String citation;
    Wildlife wildlife;
    UserData userData;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wildlife);

        // Get wildlife object passed from MapFragment through the intent
        wildlife = (Wildlife) getIntent().getSerializableExtra("WILDLIFE");
        userData = (UserData) (getIntent().getSerializableExtra("USERDATA"));

        this.image_url = wildlife.getImage_url();
        this.common_name = wildlife.getCommonName();
        this.scientific_name = wildlife.getScientificName();
        this.taxon = wildlife.getTaxon();
        this.level = wildlife.getLevel();
        this.points_worth = wildlife.getPoints();
        this.fun_fact = wildlife.getFunFact();
        this.citation = wildlife.getCitation();

        // Fields yet to be implemented within the app
        this.wildlife_nickname = wildlife.getNickname();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        userData.updateOblogElement(wildlife);
        db.collection("userData").document(user.getEmail()).set(userData);

        // Set the image
        ImageView imgView =(ImageView)findViewById(R.id.wildlife_pic);
        Bitmap bitmap = convertPicture(image_url);
        imgView.setImageBitmap(bitmap);

        // Set text fields appropriately
        TextView common = findViewById(R.id.wildlife_common);
        common.setText(getTextC(common_name));

        TextView scientific = findViewById(R.id.wildlife_scientific);
        scientific.setText(getTextS(scientific_name));
        // These have yet to be filled by csv parser
        TextView taxon = findViewById(R.id.wildlife_taxon);
        taxon.setText(getTextT(this.taxon));

        TextView level = findViewById(R.id.wildlife_level);
        level.setText(getTextL(this.level));

        TextView points = findViewById(R.id.wildlife_points);
        points.setText(getTextP(this.points_worth)+"");

        TextView nn = findViewById(R.id.wildlife_nname);
        nn.setText(getTextN(this.wildlife_nickname));

        TextView ff = findViewById(R.id.wildlife_ff);
        ff.setText(getTextF(this.fun_fact));

        //TextView cit = findViewById(R.id.wildlife_citation);
        //cit.setText(getTextCit(this.citation));

        TextView rename = findViewById(R.id.wildlife_rename);
        rename.setText("Rename: ");

        Button submitBtn = findViewById(R.id.wildlife_submit);
        submitBtn.setOnClickListener(this);

        Button returnBtn = findViewById(R.id.wildlife_return);
        returnBtn.setOnClickListener(this);

    }

    // Button handler
    public void onClick(View v) {
        if (v.getId() == R.id.wildlife_submit) {
            EditText editText = findViewById(R.id.wildlife_input);
            newName = editText.getText().toString();
            if (!newName.equals("") && newName.length() <= 30) {
                // Updates the wildlife's nickname
                wildlife.setNickname(newName);
                this.wildlife_nickname = newName;
                TextView nn = findViewById(R.id.wildlife_nname);
                nn.setText(getTextN(wildlife.getNickname()));
                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseUser user = auth.getCurrentUser();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                userData.updateOblogElement(wildlife);
                db.collection("userData").document(user.getEmail()).set(userData);
                String e = "";
                TextView error = findViewById(R.id.wildlife_error);
                error.setText(e);
            } else if (newName.equals("")) {
                wildlife.setNickname(common_name);
                this.wildlife_nickname = common_name;
                TextView nn = findViewById(R.id.wildlife_nname);
                nn.setText(getTextN(common_name));
            } else if (newName.length() > 30) {
                String e = "Cannot be more than 30 characters.";
                TextView error = findViewById(R.id.wildlife_error);
                error.setText(e);
            } else {
                String e = "Error: Invalid Nickname";
                TextView error = findViewById(R.id.wildlife_error);
                error.setText(e);
            }
        } else if (v.getId() == R.id.wildlife_return) {
            // Redirect page
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("USERDATA", userData);
            startActivity(i);
        }
    }


    // Getters that return text Strings
    public String getTextC(String text) {return "Common Name: " + text;}
    public String getTextS(String text) {return "Scientific Name: " + text;}
    public String getTextT(String text) {return "Taxon: " + text;}
    public String getTextL(String level) {return "Level: " + level;}
    public String getTextP(String points) {return "Points worth: " + points;}
    public String getTextN(String text) {return "Nickname: " + text;}
    public String getTextF(String text) {return "Fun Fact: " + text;}
    public String getTextCit(String text) {return "Citation: " + text;}

    // Convert an image url into a bitmap -- necessary to set an ImageView
    private Bitmap convertPicture(String url_given) {
        String drawableRes = url_given;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Bitmap bitmap;
        try {
            URL url = new URL(drawableRes);
            bitmap = BitmapFactory.decodeStream((InputStream)url.getContent());
        } catch (IOException e) {
            Log.e("Error converting pic", e.getMessage());
            bitmap = null;
        }
        return bitmap;

    }
}
