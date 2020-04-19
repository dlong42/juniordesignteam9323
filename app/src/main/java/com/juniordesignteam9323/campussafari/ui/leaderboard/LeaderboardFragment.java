package com.juniordesignteam9323.campussafari.ui.leaderboard;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.juniordesignteam9323.campussafari.MainActivity;
import com.juniordesignteam9323.campussafari.R;
import com.juniordesignteam9323.campussafari.UserData;
import androidx.lifecycle.Observer;
import androidx.annotation.Nullable;
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
    public List<LeaderboardDataModel> leaderBoard = new ArrayList<LeaderboardDataModel>();
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private UserData userData;
    private static RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        userData = ((MainActivity) getActivity()).getUserData();
        leaderboardViewModel =
                ViewModelProviders.of(this, new LeaderboardViewModel(userData))
                        .get(LeaderboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_leaderboard, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = (RecyclerView) getView().findViewById(R.id.rvLeaders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //create list of all players as leaderboard datamodels
        db.collection("userData").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.getData().get("nickname") != null) {
                            leaderBoard.add(new LeaderboardDataModel(document.getData().get("nickname").toString(),
                                    Integer.valueOf(document.getData().get("points").toString())));
                        }
                    }
                    Log.d("LeaderBoard: ", leaderBoard.toString());
                } else {
                    Log.d(TAG, "Error getting docs: ", task.getException());
                }
                Collections.sort(leaderBoard, new SortByPoints());
                Log.d("LeaderBoard sorted : ", leaderBoard.get(0).getName());
                ArrayList<LeaderboardDataModel> data = new ArrayList<LeaderboardDataModel>();
                if (leaderBoard.size() > 10) {
                    for (int i = 0; i < 10; i++){
                        data.add(leaderBoard.get(i));
                    }
                } else {
                    data = (ArrayList<LeaderboardDataModel>) leaderBoard;
                }
                adapter = new LeaderboardAdapter(data);
                recyclerView.setAdapter(adapter);
            }
        });
    }

    public List getLeaderBoard() {return leaderBoard;}

    // Sorts leaderBoard from highest to lowest points
    public class SortByPoints implements Comparator {
        public int compare(Object o1, Object o2) {
            LeaderboardDataModel m1 = (LeaderboardDataModel) o1;
            LeaderboardDataModel m2 = (LeaderboardDataModel) o2;
            return Integer.compare(m2.getPoints(), m1.getPoints());
        }
    }

}