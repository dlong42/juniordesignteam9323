package com.juniordesignteam9323.campussafari.ui.oblog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.juniordesignteam9323.campussafari.R;
import com.juniordesignteam9323.campussafari.ui.friends.FriendsViewModel;

public class OblogFragment extends Fragment {

    private OblogViewModel oblogViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        oblogViewModel =
                ViewModelProviders.of(this).get(OblogViewModel.class);
        View root = inflater.inflate(R.layout.fragment_oblog, container, false);
        final TextView textView = root.findViewById(R.id.text_oblog);
        oblogViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}