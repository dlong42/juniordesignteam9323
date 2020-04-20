package com.juniordesignteam9323.campussafari.ui.achievements;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.juniordesignteam9323.campussafari.Achievement;
import com.juniordesignteam9323.campussafari.MainActivity;
import com.juniordesignteam9323.campussafari.R;
import com.juniordesignteam9323.campussafari.UserData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * This fragment displays achievements and progress towards them.
 */
public class AchievementsFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    private AchievementsViewModel achievementsViewModel;
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<AchievementDataModel> data;
    static View.OnClickListener myOnClickListener;
    private UserData userData;
    private ArrayList<Achievement> achievements;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        userData = ((MainActivity) getActivity()).getUserData();
        achievements = userData.getAchievements();
        achievementsViewModel = ViewModelProviders.of(this, new AchievementsViewModel(userData))
                .get(AchievementsViewModel.class);

        View root = inflater.inflate(R.layout.fragment_achievements, container, false);
        final TextView textView = root.findViewById(R.id.text_achievements);

        //setting up spinner
        Spinner achievementSpinner = (Spinner) root.findViewById(R.id.achievement_spinner);
        ArrayAdapter<CharSequence> spinAdapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.achievement_sortby, android.R.layout.simple_spinner_dropdown_item);
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        achievementSpinner.setAdapter(spinAdapter);
        achievementSpinner.setOnItemSelectedListener(this);

        return root;

    }


    //setting up the recycler view
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = (RecyclerView) getView().findViewById(R.id.rvAchievement);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        //makes a datamodel for each achievement, then adds to data Arraylist which is sent to
        //adapter to put into Achievement's Recycler view
        data = new ArrayList<AchievementDataModel>();
        for (int i = 0; i < achievements.size(); i++) {
            if (achievements.get(i).getName() == null) {
                System.out.println("Achievement Name Null");
            }
            if (achievements.get(i).getNeeded().size() == 0) {
                System.out.println("Achievement needs are empty");
            }
            Drawable d;
            Resources res = getResources();
            if (achievements.get(i).isAchieved()) {
                d = res.getDrawable(R.drawable.ic_trophy);
            } else {
                d = res.getDrawable(R.drawable.ic_trophy_gray);
            }
            data.add(new AchievementDataModel(
                    achievements.get(i).getName(),
                    achievements.get(i).isAchieved(),
                    achievements.get(i).getNeeded(),
                    achievements.get(i).getCount(),
                    achievements.get(i).getDescription(),
                    d
            ));
            System.out.println("Achievement Data Model:" + data.get(i).getName()+ data.get(i).isAchieved() + data.get(i).getNeeded().size() + data.get(i).getDescription());
        }
        adapter = new AchievementAdapter(data);
        recyclerView.setAdapter(adapter);
    }


    public ArrayList<AchievementDataModel> updateData(){
        ArrayList<AchievementDataModel> updatedData = new ArrayList<>();
        for (int i = 0; i < achievements.size(); i++) {
            if (achievements.get(i).getName() == null) {
                System.out.println("Achievement Name Null");
            }
            if (achievements.get(i).getNeeded().size() == 0) {
                System.out.println("Achievement needs are empty");
            }
            Drawable d;
            Resources res = getResources();
            if (achievements.get(i).isAchieved()) {
                d = res.getDrawable(R.drawable.ic_trophy);
            } else {
                d = res.getDrawable(R.drawable.ic_trophy_gray);
            }
            updatedData.add(new AchievementDataModel(
                    achievements.get(i).getName(),
                    achievements.get(i).isAchieved(),
                    achievements.get(i).getNeeded(),
                    achievements.get(i).getCount(),
                    achievements.get(i).getDescription(),
                    d
            ));
            System.out.println("Updated Achievement Data Model:" + data.get(i).getName()+ data.get(i).isAchieved() + data.get(i).getNeeded().size() + data.get(i).getDescription());
        }
        return updatedData;
    }

    //Updates the selection of sorting method for achievements.
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        String text = adapterView.getItemAtPosition(position).toString();
        if (text.equals("Title")){
            System.out.println("Title sort clicked");
            SortByTitle sortAlgoTitle  = new SortByTitle();
            Collections.sort(achievements, sortAlgoTitle);
        }
        else if (text.equals("Most Progress")){
            System.out.println("Common name clicked");
            SortByMostProgress sortAlgoMProgress = new SortByMostProgress();
            Collections.sort(achievements, sortAlgoMProgress);
        }
        else if (text.equals("Least Progress")){
            SortByLeastProgress sortAlgoLProgress = new SortByLeastProgress();
            Collections.sort(achievements, sortAlgoLProgress);
        }
        data = updateData();
        adapter.notifyDataSetChanged();
        recyclerView.invalidate();
        recyclerView.setAdapter(new AchievementAdapter(data));
        Toast.makeText(adapterView.getContext(), "Sorted by " + text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    // Sorts achievements by title
    public class SortByTitle implements Comparator<Achievement> {
        public int compare(Achievement a1, Achievement a2) {
            System.out.println("Title sort started");
            if (a1.getName().compareTo(a2.getName()) > 0) {
                System.out.println("Title sort as 1");
                return 1;
            } else if (a1.getName().compareTo(a2.getName()) < 0) {
                System.out.println("Title sort as -1");
                return -1;
            } else {
                System.out.println("Title sort as 0");
                return 0;
            }
        }
    }

    // Sorts achievements by most progress
    public class SortByMostProgress implements Comparator<Achievement> {
        public int compare(Achievement a1, Achievement a2) {
            if (((float) a1.getCount() / (float) a1.getNeeded().size()) > ((float) a2.getCount() / (float)a2.getNeeded().size())) {
                return -1;
            } else if (((float) a1.getCount() / (float) a1.getNeeded().size()) < ((float) a2.getCount() / (float)a2.getNeeded().size())) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    // Sorts achievements by least progress
    public class SortByLeastProgress implements Comparator<Achievement> {
        public int compare(Achievement a1, Achievement a2) {
            if (((float) a1.getCount() / a1.getNeeded().size()) > ((float) a2.getCount() / a2.getNeeded().size())) {
                return 1;
            } else if (((float) a1.getCount() / a1.getNeeded().size()) < ((float) a2.getCount() / a2.getNeeded().size())) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}