package com.juniordesignteam9323.campussafari.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
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


        final RadioGroup radioGroup = findViewById(R.id.avatarButtons);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.owl) {
                    userData.setAvatar("owl");
                    Snackbar owlSnack = Snackbar.make(radioGroup, "Selected the owl!", Snackbar.LENGTH_LONG);
                    View owlSnackView = owlSnack.getView();
                    FrameLayout.LayoutParams owlSnackParams = (FrameLayout.LayoutParams) owlSnackView.getLayoutParams();
                    owlSnackParams.gravity = Gravity.TOP;
                    owlSnackView.setLayoutParams(owlSnackParams);
                    owlSnack.show();
                } else if (checkedId == R.id.bear) {
                    userData.setAvatar("bear");
                    Snackbar bearSnack = Snackbar.make(radioGroup, "Selected the bear!", Snackbar.LENGTH_LONG);
                    View bearSnackView = bearSnack.getView();
                    FrameLayout.LayoutParams bearSnackParams = (FrameLayout.LayoutParams) bearSnackView.getLayoutParams();
                    bearSnackParams.gravity = Gravity.TOP;
                    bearSnackView.setLayoutParams(bearSnackParams);
                    bearSnack.show();
                } else if (checkedId == R.id.chameleon) {
                    userData.setAvatar("chameleon");
                    Snackbar chamSnack = Snackbar.make(radioGroup, "Selected the chameleon!", Snackbar.LENGTH_LONG);
                    View chamSnackView = chamSnack.getView();
                    FrameLayout.LayoutParams chamSnackParams = (FrameLayout.LayoutParams) chamSnackView.getLayoutParams();
                    chamSnackParams.gravity = Gravity.TOP;
                    chamSnackView.setLayoutParams(chamSnackParams);
                    chamSnack.show();
                } else if (checkedId == R.id.raccoon) {
                    userData.setAvatar("raccoon");
                    Snackbar racSnack = Snackbar.make(radioGroup, "Selected the raccoon!", Snackbar.LENGTH_LONG);
                    View racSnackView = racSnack.getView();
                    FrameLayout.LayoutParams racSnackParams = (FrameLayout.LayoutParams) racSnackView.getLayoutParams();
                    racSnackParams.gravity = Gravity.TOP;
                    racSnackView.setLayoutParams(racSnackParams);
                    racSnack.show();
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
