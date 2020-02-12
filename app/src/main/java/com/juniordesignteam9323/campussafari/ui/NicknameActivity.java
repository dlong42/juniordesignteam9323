package com.juniordesignteam9323.campussafari.ui;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.juniordesignteam9323.campussafari.MainActivity;
import com.juniordesignteam9323.campussafari.R;
import com.juniordesignteam9323.campussafari.ui.profile.ProfileFragment;

import java.util.HashMap;
import java.util.Map;

//this class handles the change of  a user nickname, called displayName in the Firestore database
public class NicknameActivity extends AppCompatActivity implements View.OnClickListener{

    FirebaseAuth auth;
    FirebaseUser user;
    String newName;
    String oldName;
    TextView textView;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nickname);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        textView = findViewById(R.id.editText);
        oldName = user.getDisplayName();
        textView.setText(oldName);

        Button cancelBtn = findViewById(R.id.nickname_cancel);
        cancelBtn.setOnClickListener(this);

        Button submitBtn = findViewById(R.id.nickname_submit);
        submitBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);

    }

    // Button handler
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nickname_cancel:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.nickname_submit:
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                EditText editText = findViewById(R.id.editText);
                newName = editText.getText().toString();
                if (!newName.equals("")) {
                    // Updates the user attribute displayName
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(newName)
                            .build();
                    user.updateProfile(profileUpdates);

                    // Update new user document in database with correct displayName
                    Map<String, Object> updates = new HashMap<>();
                    updates.put("displayName", newName);
                    db.collection("users").document(user.getEmail()).update(updates);
                    // Redirect page
                    startActivity(new Intent(this, MainActivity.class));
                } else {
                    String e = "Error: Invalid Nickname";
                    TextView error = findViewById(R.id.nickname_error);
                    error.setText(e);
                }
                break;
            default:
                break;
        }
    }

}
