package com.juniordesignteam9323.campussafari.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.juniordesignteam9323.campussafari.MainActivity;
import com.juniordesignteam9323.campussafari.R;
import com.juniordesignteam9323.campussafari.UserData;

import java.util.HashMap;
import java.util.Map;

public class ProfileCreationActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseFirestore db;
    UserData userData;
    Button finishButton;
    EditText nicknameEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilecreation);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        userData = (UserData) (getIntent().getSerializableExtra("USERDATA"));

        finishButton = findViewById(R.id.pcreate_finishbutton);
        finishButton.setEnabled(false);
        finishButton.setOnClickListener(this);

        nicknameEdit = findViewById(R.id.pcreate_nicknameEdit);

        // Necessary to make the finish button disabled if no text is entered
        nicknameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    finishButton.setEnabled(false);
                } else {
                    finishButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        RadioGroup radioGroup = findViewById(R.id.avatarButtons);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.owl) {
                    userData.setAvatar("owl");
                } else if (checkedId == R.id.bear) {
                    userData.setAvatar("bear");
                } else if (checkedId == R.id.chameleon) {
                    userData.setAvatar("chameleon");
                } else if (checkedId == R.id.raccoon) {
                    userData.setAvatar("raccoon");
                }
            }
        });
    }

    public void onClick(View v) {

        // Note: currently this code both updates the field in UserData as well as the user document
        // displayName in database - we should have a conversation about which one we want to actually
        // use as nickname and choose one.

        // Updates the userdata nickname field
        String nickname = nicknameEdit.getText().toString();
        userData.setNickname(nickname);

        // Updates the user attribute displayName
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(nickname)
                .build();
        user.updateProfile(profileUpdates);

        // Update new user document in database with correct displayName
        Map<String, Object> updates = new HashMap<>();
        updates.put("displayName", nickname);
        db.collection("users").document(user.getEmail()).update(updates);

        db.collection("userData").document(user.getEmail()).set(userData);

        // Navigate to the main game
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("USERDATA", userData);
        startActivity(intent);
    }
}
