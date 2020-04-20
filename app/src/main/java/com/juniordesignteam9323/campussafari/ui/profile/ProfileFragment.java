package com.juniordesignteam9323.campussafari.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.juniordesignteam9323.campussafari.MainActivity;
import com.juniordesignteam9323.campussafari.R;
import com.juniordesignteam9323.campussafari.UserData;
import com.juniordesignteam9323.campussafari.ui.NicknameActivity;
import com.juniordesignteam9323.campussafari.ui.PasswordActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

/**
 * This fragment manages user settings and profile changes.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    private ProfileViewModel profileViewModel;

    public View view;
    public UserData userData;

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

        //This code allows users to turn music on and off within the app.
        Switch musicToggle = root.findViewById(R.id.musicSwitch);
        musicToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity != null) {
                    if (isChecked) {
                        mainActivity.startMusic();
                    } else {
                        mainActivity.stopMusic();
                    }
                }
            }
        });

        ///users can change username and password
        Button change_nicknameBtn = root.findViewById(R.id.change_nickname);
        Button change_psswrdBtn = root.findViewById(R.id.change_password);
        change_nicknameBtn.setOnClickListener(this);
        change_psswrdBtn.setOnClickListener(this);

        userData = ((MainActivity) getActivity()).getUserData();

        // Set avatar based on what user selected
        ImageView avatar = root.findViewById(R.id.imageView4);
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

        return root;
    }


    //Listener for nickname/password changes
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.change_nickname:
                Intent i = new Intent(getActivity(), NicknameActivity.class);
                i.putExtra("USERDATA", userData);
                startActivity(i);
                break;
            case R.id.change_password:
                Intent j = new Intent(getActivity(), PasswordActivity.class);
                j.putExtra("USERDATA", userData);
                startActivity(j);
                break;
            default:
                break;
        }
    }
}