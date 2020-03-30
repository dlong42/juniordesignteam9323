package com.juniordesignteam9323.campussafari;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.juniordesignteam9323.campussafari.ui.LoginActivity;
import com.juniordesignteam9323.campussafari.ui.ProfileCreationActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.Objects;

import static com.firebase.ui.auth.AuthUI.TAG;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private UserData userData;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private  FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseUser user = auth.getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        userData = (UserData) (getIntent().getSerializableExtra("USERDATA"));
        if (userData == null){
            Log.d("Userdata", "null");
        } else {
            Log.d("Userdata", "Admin: " + userData.getAdmin() + "Email: " + userData.getEmail());
        }

        if (userData == null) {
            userData = new UserData(true, "weigel@gmail.com");
        }

        Log.d("admin set up", "status: "+ userData.getAdmin() + " Email: " + userData.getEmail());


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
                R.id.nav_oblog, R.id.nav_map, R.id.nav_oblog, R.id.nav_achievements, R.id.nav_profile,
                R.id.nav_leaderboard, R.id.nav_friends, R.id.nav_admin, R.id.nav_settings, R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        CSVParse parsey = new CSVParse("observations-75146.csv", getApplicationContext());

    }

    //Set avatar to be in the top left of the nav bar
    public void setAvatarBar(NavigationView navigationView) {

        navigationView.getMenu().findItem(R.id.nav_admin).setVisible(userData.getAdmin());

        //Set avatar to be in the top left of the nav bar
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
    }

    public UserData getUserData() {
        return this.userData;
    }

    public void addToObLog(Wildlife wl) {
        this.getUserData().addToObLog(wl);
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
