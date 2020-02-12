package com.juniordesignteam9323.campussafari;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.juniordesignteam9323.campussafari.ui.LoginActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private boolean admin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkLocationPermission();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                NavigationView navigationView = findViewById(R.id.nav_view);
                if (admin == false) {
                    admin = true;
                    navigationView.getMenu().findItem(R.id.nav_admin).setVisible(true);
                } else {
                    admin = false;
                    navigationView.getMenu().findItem(R.id.nav_admin).setVisible(false);
                }
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        if (admin == false) {
            navigationView.getMenu().findItem(R.id.nav_admin).setVisible(false);
        }

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_profile, R.id.nav_map,  R.id.nav_oblog, R.id.nav_achievements,
                R.id.nav_leaderboard, R.id.nav_friends, R.id.nav_reports, R.id.nav_settings)
                
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //navigationView.setNavigationItemSelectedListener(this);


        CSVParse parsey = new CSVParse("observations-75146.csv", getApplicationContext());
    }

//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//         //Handle navigation view item clicks here.
//        Fragment fragment = null;
//        int id = item.getItemId();
//        if (id == R.id.nav_achievements) {
//            fragment = new AchievementsFragment();
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.add(id, fragment);
//            ft.commit();
//            //logout();
//        }
//        } else {
//            fragment = new AchievementsFragment();
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.replace(id, fragment);
//            ft.commit();
//        }
//        if (id == R.id.nav_achievements) {
//            fragment = new Fragment();
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.nav_achievements, fragment);
//            ft.commit();
//        }
//        if (id == R.id.nav_map) {
//            fragment = new Fragment();
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.nav_map, fragment);
//            ft.commit();
//        }
//        if (id == R.id.nav_settings) {
//            fragment = new Fragment();
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.nav_settings, fragment);
//            ft.commit();
//        }if (id == R.id.nav_oblog) {
//            fragment = new Fragment();
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.nav_oblog, fragment);
//            ft.commit();
//        }
//        if (id == R.id.nav_admin) {
//            fragment = new Fragment();
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.nav_admin, fragment);
//            ft.commit();
//        }
//        if (id == R.id.nav_leaderboard) {
//            fragment = new Fragment();
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.nav_leaderboard, fragment);
//            ft.commit();
//        }
//        if (id == R.id.nav_friends) {
//            fragment = new Fragment();
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.nav_friends, fragment);
//            ft.commit();
//        }
//        if (id == R.id.nav_log) {
//            fragment = new Fragment();
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.nav_log, fragment);
//            ft.commit();
//        }
//        return true;
//    }



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



    //sign out method
    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }



}
