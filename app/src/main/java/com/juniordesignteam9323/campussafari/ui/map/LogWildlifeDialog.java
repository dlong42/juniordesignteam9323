package com.juniordesignteam9323.campussafari.ui.map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.juniordesignteam9323.campussafari.R;

public class LogWildlifeDialog extends AppCompatDialogFragment {
    private EditText editTextNickname;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.log_wildlife_dialog, null);

        builder.setView(view)
                .setTitle("Wildlife Logged!")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String nickname = editTextNickname.getText().toString();
                        Intent intent = new Intent();
                        intent.putExtra("nickname", nickname);
                        // Sends the nickname data to the MapFragment
                        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                    }
                });

        editTextNickname = view.findViewById(R.id.wildlife_nickname);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

}
