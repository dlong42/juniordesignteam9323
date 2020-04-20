package com.juniordesignteam9323.campussafari;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * Creates a dialog box to display when the user levels up.
 */
public class LevelUpDialog extends DialogFragment {

    private Intent intent;

    public LevelUpDialog(Intent intent) {
        this.intent = intent;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Congrats, you're now Level " + ((MainActivity) getActivity()).getUserData().getLevel() + "! There is new wildlife to discover around campus.")
                .setTitle("Level Up!")
                .setPositiveButton("Let's explore!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(intent);
                    }
                });
        return builder.create();
    }
}
