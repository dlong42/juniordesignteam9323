package com.juniordesignteam9323.campussafari.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.juniordesignteam9323.campussafari.MainActivity;
import com.juniordesignteam9323.campussafari.R;
import com.juniordesignteam9323.campussafari.ui.LoginActivity;
import com.juniordesignteam9323.campussafari.ui.NicknameActivity;
import com.juniordesignteam9323.campussafari.ui.PasswordActivity;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private ProfileViewModel profileViewModel;

    public View view;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);

        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        final TextView textView = root.findViewById(R.id.text_profile);
        profileViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });


        Button change_nicknameBtn = root.findViewById(R.id.change_nickname);
        Button change_psswrdBtn = root.findViewById(R.id.change_password);
        change_nicknameBtn.setOnClickListener(this);
        change_psswrdBtn.setOnClickListener(this);

        return root;
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.change_nickname:
                startActivity(new Intent(getActivity(), NicknameActivity.class));
                break;
            case R.id.change_password:
                startActivity(new Intent(getActivity(), PasswordActivity.class));
                break;
            default:
                break;
        }
    }
}