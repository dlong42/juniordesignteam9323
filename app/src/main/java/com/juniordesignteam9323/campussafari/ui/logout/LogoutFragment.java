package com.juniordesignteam9323.campussafari.ui.logout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.juniordesignteam9323.campussafari.R;
import com.juniordesignteam9323.campussafari.ui.LoginActivity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * Logs the user out when they get here.
 */
public class LogoutFragment extends Fragment{
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_achievements, container, false);
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        return root;
    }
}
