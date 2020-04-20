package com.juniordesignteam9323.campussafari;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

/**
 * This is where the userData is stored while the app is running.
 */
public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private UserData userData;
    private TextView levelView;
    private TextView pointsView;
    private ProgressBar xpBar;
    private MediaPlayer music;


    /**
     * This runs as soon as the user is logged in.
     * @param savedInstanceState holds data if the phone closes the app
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Recieve userdata from log in, log back out if it is null.
        userData = (UserData) (getIntent().getSerializableExtra("USERDATA"));
        if (userData == null){
            Log.d("Userdata", "null");
        } else {
            Log.d("Userdata", "Admin: " + userData.getAdmin() + "Email: " + userData.getEmail());


            Log.d("admin set up", "status: " + userData.getAdmin() + " Email: " + userData.getEmail());


            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            /**FloatingActionButton fab = findViewById(R.id.fab);
             fab.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();

            }
            });*/
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            NavigationView navigationView = findViewById(R.id.nav_view);

            navigationView.getMenu().findItem(R.id.nav_admin).setVisible(userData.getAdmin());

            // Set avatar to be in the top left of the nav bar
            View hView = navigationView.getHeaderView(0);
            ImageView avatar = hView.findViewById(R.id.imageView);

            String avatarId = userData.getAvatar();
            if (avatarId.equals("owl")) {
                avatar.setImageResource(R.drawable.avatar_owl);
            } else if (avatarId.equals("bear")) {
                avatar.setImageResource(R.drawable.avatar_bear);
            } else if (avatarId.equals("chameleon")) {
                avatar.setImageResource(R.drawable.avatar_chameleon);
            } else if (avatarId.equals("raccoon")) {
                avatar.setImageResource(R.drawable.avatar_raccoon);
            }

            // Sets the info on the navbar header to the user's current nickname, points, and level
            TextView nicknameView = hView.findViewById(R.id.nickname);
            levelView = hView.findViewById(R.id.level);
            pointsView = hView.findViewById(R.id.points);
            xpBar = hView.findViewById(R.id.xpBar);

            nicknameView.setText(userData.getNickname());
            levelView.setText("Level: " + userData.getLevel());
            pointsView.setText("Points: " + userData.getPoints() + "/" + UserData.levelThreshold(userData.getLevel()));
            xpBar.setMax(UserData.levelThreshold(userData.getLevel()));
            xpBar.setProgress(userData.getPoints());


            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_oblog, R.id.nav_map, R.id.nav_oblog, R.id.nav_achievements, R.id.nav_profile,
                    R.id.nav_leaderboard,  R.id.nav_admin, R.id.nav_settings, R.id.nav_logout)
                    .setDrawerLayout(drawer)
                    .build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
            NavigationUI.setupWithNavController(navigationView, navController);

            CSVParse parsey = new CSVParse("observations-75146.csv", getApplicationContext());
        }

        music = MediaPlayer.create(getApplicationContext(), R.raw.music);
        music.setLooping(true);
        music.start();
    }

    public UserData getUserData() {
        return this.userData;
    }

    public void addToObLog(Wildlife wl) {
        this.getUserData().addToObLog(wl);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        return;
    }

    @Override
    protected void onPause() {
        super.onPause();
        music.stop();
        music.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        music = MediaPlayer.create(getApplicationContext(), R.raw.music);
        music.setLooping(true);
        music.start();
    }

    public TextView getLevelView() {
        return levelView;
    }

    public TextView getPointsView() {
        return pointsView;
    }

    public void setLevelView(TextView levelView) {
        this.levelView = levelView;
    }

    public void setPointsView(TextView pointsView) {
        this.pointsView = pointsView;
    }

    public ProgressBar getXpBar() {
        return xpBar;
    }

    public void setXpBar(ProgressBar xpBar) {
        this.xpBar = xpBar;
    }

    public void startMusic() {
        music = MediaPlayer.create(getApplicationContext(), R.raw.music);
        music.setLooping(true);
        music.start();
    }

    public void stopMusic() {
        music.stop();
        music.release();
    }
}
