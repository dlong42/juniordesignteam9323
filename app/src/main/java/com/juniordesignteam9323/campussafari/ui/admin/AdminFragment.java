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

public class AdminFragment extends Fragment {

    private AdminViewModel adminViewModel;
    public String adminEmail;
    public final FirebaseFirestore db = FirebaseFirestore.getInstance();;

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


        final TextView emailLabel = (TextView) root.findViewById(R.id.emailLabel);
        final EditText emailBox = (EditText) root.findViewById(R.id.enterEmail);
        final Button submit = (Button) root.findViewById(R.id.submitAdmin);

        if (!((MainActivity) getActivity()).getUserData().getEmail().equals("weigel@gmail.com")) {
            emailBox.setVisibility(View.GONE);
            submit.setVisibility(View.GONE);
            emailLabel.setVisibility(View.GONE);
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



        return root;
    }
}