package com.juniordesignteam9323.campussafari.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.juniordesignteam9323.campussafari.MainActivity;
import com.juniordesignteam9323.campussafari.R;
import com.juniordesignteam9323.campussafari.UserData;

import androidx.appcompat.app.AppCompatActivity;

/**
 * The user is brought here if they want to change their password.
 */
public class PasswordActivity extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth auth;
    FirebaseUser user;
    String newName;
    String oldName;
    TextView textView;
    FirebaseFirestore db;
    UserData userData;

    //Set up all the buttons
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        userData = (UserData) (getIntent().getSerializableExtra("USERDATA"));

        Button cancel = findViewById(R.id.psswrd_cancel);
        cancel.setOnClickListener(this);
        Button submit = findViewById(R.id.psswrd_update);
        submit.setOnClickListener(this);
    }

    // Button handler
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.psswrd_cancel:
                //Redirect page
                Intent i = new Intent(this, MainActivity.class);
                i.putExtra("USERDATA", userData);
                startActivity(i);
                break;
            case R.id.psswrd_update:
                //take user input of old and new password fields

                EditText editText3 = findViewById(R.id.editText3);
                EditText editText4 = findViewById(R.id.editText4);
                String new1 = editText3.getText().toString();
                String new2 = editText4.getText().toString();
                if (new1.equals(new2) & !new1.equals("")  & new1.length()>7) {
                    // Update user password
                    user.updatePassword(new1);
                    // Update new user document in database with correct password
                    final String TAG = "LoginActivity";
                    db.collection("users").document(user.getEmail()).set(user)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "DocumentSnapshot added with ID: " + user.getEmail());
                                }
                            });
                    // Redirect page
                    Intent i2 = new Intent(this, MainActivity.class);
                    i2.putExtra("USERDATA", userData);
                    startActivity(i2);
                }else if (new1.length() < 8) {
                    String e = "Error: password must be at least 8 characters long.";
                    TextView error = findViewById(R.id.psswrd_error);
                    error.setText(e);
                }else if (!new1.equals(new2)){
                    String e = "Error: either old passwords or new passwords do not match.";
                    TextView error = findViewById(R.id.psswrd_error);
                    error.setText(e);
                } else {
                    String e = "Error: cannot leave fields blank.";
                    TextView error = findViewById(R.id.psswrd_error);
                    error.setText(e);
                }
                break;
            default:
                break;
        }
    }
}
