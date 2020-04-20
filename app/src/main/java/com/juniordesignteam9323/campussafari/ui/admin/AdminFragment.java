package com.juniordesignteam9323.campussafari.ui.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.juniordesignteam9323.campussafari.MainActivity;
import com.juniordesignteam9323.campussafari.R;
import com.juniordesignteam9323.campussafari.UserData;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

/**
 * This fragment contains information for admins to access our app.
 */
public class AdminFragment extends Fragment {

    private AdminViewModel adminViewModel;
    public String adminEmail;
    public final FirebaseFirestore db = FirebaseFirestore.getInstance();;
    String login_d;
    String users_d;
    String time_d;
    String add_d;
    String remove_d;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        adminViewModel =
                ViewModelProviders.of(this).get(AdminViewModel.class);
        View root = inflater.inflate(R.layout.fragment_admin, container, false);
        final TextView textView = root.findViewById(R.id.text_admin);
        adminViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        // Buttons and text only viewable by Dr. Weigel
        final TextView emailLabel = (TextView) root.findViewById(R.id.emailLabel);
        final EditText emailBox = (EditText) root.findViewById(R.id.enterEmail);
        final Button submit = (Button) root.findViewById(R.id.submitAdmin);

        // Buttons and text visible to all admin
        final Button login = (Button) root.findViewById(R.id.admin_login);
        final Button admin_users = (Button) root.findViewById(R.id.admin_user);
        final Button admin_time = (Button) root.findViewById(R.id.admin_time);
        final Button admin_addpin = (Button) root.findViewById(R.id.admin_addpin);
        final Button admin_removepin = (Button) root.findViewById(R.id.admin_removepin);
        final TextView admin_directions = (TextView) root.findViewById(R.id.admin_directions);
        final TextView admin_howto = (TextView) root.findViewById(R.id.admin_howto);
        admin_howto.setText("How To:");

        // Sets direction text base on button clicked
        login.setOnClickListener(new View.OnClickListener() {public void onClick(View v) {admin_directions.setText(login_d);}});
        admin_users.setOnClickListener(new View.OnClickListener() {public void onClick(View v) {admin_directions.setText(users_d);}});
        admin_time.setOnClickListener(new View.OnClickListener() {public void onClick(View v) {admin_directions.setText(time_d);}});
        admin_addpin.setOnClickListener(new View.OnClickListener() {public void onClick(View v) {admin_directions.setText(add_d);}});
        admin_removepin.setOnClickListener(new View.OnClickListener() {public void onClick(View v) {admin_directions.setText(remove_d);}});

        // Hide buttons only meant for Dr. Wiegel if the user does not have her email
        if (!((MainActivity) getActivity()).getUserData().getEmail().equals("emily.weigel@biosci.gatech.edu")) {
            emailBox.setVisibility(View.GONE);
            submit.setVisibility(View.GONE);
            emailLabel.setVisibility(View.GONE);
        } else {
            emailBox.setVisibility(View.VISIBLE);
            submit.setVisibility(View.VISIBLE);
            emailLabel.setVisibility(View.VISIBLE);
        }

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                adminEmail = emailBox.getText().toString();
                UserData userData = new UserData(true, adminEmail);
                db.collection("userData").document(adminEmail).set(userData);
                Toast.makeText(getActivity().getApplicationContext(),adminEmail + " is now an admin.",Toast.LENGTH_SHORT).show();


            }
        });

        // Texts settings for directions
        login_d = "Log into the database\n" +
                "\n1. Navigate to console.firebase.google.com under your email that was approved. If you have not gotten your email added as an editor of the database, contact Dr. Weigel before moving forward.\n" +
                "2. Select the database named Campus-Safari-Database. You have now reached the database.\n" +
                "3. The sidebar on the left hand side contains different elements of the database you can explore.\n";
        users_d = "See User data\n" +
                "\n1. Once logged into the database, select the Database option from the left hand side bar.\n" +
                "2. Select Cloud Firestore.\n" +
                "3. Here under the Data tab, you can see the documents “users” and “userData.” “users” consists of each user’s general data, located by a user’s email. “userData” consists of gameplay data from the app, also located by a user’s email.\n";
        time_d = "Locate Timestamps\n" +
                "\n1. Once logged into the database,select the Authentication tab from the left hand side bar.\n" +
                "2. Here you may find the date a user created their account and when they last accessed that account. \n" +
                "3. Note that the “timestamp” field under a given user in the document “users” contains more information down to the exact seconds of access, but this number must be first converted to date format.\n";
        add_d = "Not implemented";
        remove_d = "Not implemented";

        return root;
    }
}