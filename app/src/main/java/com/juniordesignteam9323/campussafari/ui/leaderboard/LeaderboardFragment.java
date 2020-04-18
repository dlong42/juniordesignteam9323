package com.juniordesignteam9323.campussafari.ui.leaderboard;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.juniordesignteam9323.campussafari.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import static com.firebase.ui.auth.AuthUI.TAG;

public class LeaderboardFragment extends Fragment {

    private LeaderboardViewModel leaderboardViewModel;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public List<BoardMember> leaderBoard = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        leaderboardViewModel =
                ViewModelProviders.of(this).get(LeaderboardViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_leaderboard, container, false);
        final LifecycleOwner lf = this;

        //create list of all players in the format of BoardMember objects
        db.collection("userData").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            private Class<LeaderboardViewModel> leaderboardViewModelClass;

            @SuppressLint("RestrictedApi")
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.getData().get("nickname") != null) {
                            leaderBoard.add(new BoardMember(document.getData().get("nickname").toString(),
                                    Integer.valueOf(document.getData().get("points").toString())));
                        }

                    }
                    Log.d("LeaderBoard: ", leaderBoard.toString());
                } else {
                    Log.d(TAG, "Error getting docs: ", task.getException());
                }
                Collections.sort(leaderBoard, new SortByPoints());
                if (leaderBoard.size() > 10) {
                    leaderBoard = leaderBoard.subList(0,11);
                }
                Log.d("LeaderBoard sorted : ", leaderBoard.get(0).getName());
                final TextView textView = root.findViewById(R.id.brd);
                leaderboardViewModel.getText().observe(lf, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        for (BoardMember b : leaderBoard) {
                          s += b.getDisplay();
                        }
                        textView.setText(s);
                    }
                });
            }
        });
        return root;
    }

    public List getLeaderBoard() {return leaderBoard;}

    // Object that represent members of the board - enables convenience of sorting
    public class BoardMember {
        private String name;
        private int points;
        private BoardMember(String name, int points) {
            this.name = name;
            this.points = points;
        }
        // Only getters are necessary for this class' purpose
        public int getPoints() { return points; }
        public String getName() { return name;}
        public String getDisplay() {
            String s = " " + name + " : " + points + "\n" +
                    "-------------------------------------------------------------" + "\n";
            return s;
        }
    }

    // Sorts leaderBoard from highest to lowest points
    public class SortByPoints implements Comparator {
        public int compare(Object o1, Object o2) {
            BoardMember m1 = (BoardMember) o1;
            BoardMember m2 = (BoardMember) o2;
            return Integer.compare(m2.getPoints(), m1.getPoints());
        }
    }

}