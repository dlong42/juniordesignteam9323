package com.juniordesignteam9323.campussafari;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

//import com.opencsv.CSVReader;
//import java.io.IOException;
//import java.io.FileReader;
//import java.io.*;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_profile, R.id.nav_map, R.id.nav_oblog, R.id.nav_achievements,
                R.id.nav_leaderboard, R.id.nav_friends, R.id.nav_reports, R.id.nav_settings)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        CSVParse parsey = new CSVParse("observations-75146.csv", getApplicationContext());
        //ArrayList<ArrayList<String>> temp = parsey.getList(new int[]{37, 36});

        //printCSV();
    }

//    public void printCSV() {
//        try {
//            //String csvfileString = "/Users/BrianZhu/Desktop/Georgia Tech/Courses/Spring 2020/Junior Design/juniordesignteam9323/app/src/main/assets/"  + "observations-75146.csv";
//            //Log.d("myTag", csvfileString);
//            //File csvfile = new File(csvfileString);
//            CSVReader reader = new CSVReader(new InputStreamReader(getAssets().open("observations-75146.csv")));
//            String[] nextLine;
//            while ((nextLine = reader.readNext()) != null) {
//                // nextLine[] is an array of values from the line
//                Pin p = new Pin(nextLine[20], nextLine[21]);
//                Log.d("myTag", nextLine[36]);
//            }
//        }
//        catch (IOException ex) {
//            // handle exception
//            Log.d("myTag", ex.toString());
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }



}
