package com.juniordesignteam9323.campussafari.ui.achievements;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.juniordesignteam9323.campussafari.R;

import java.util.ArrayList;

public class AchievementAdapter extends RecyclerView.Adapter<AchievementAdapter.MyViewHolder> {

    private ArrayList<AchievementDataModel> dataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        TextView textAchievementName;
        TextView textAchieved;
        TextView textNeeded;
        TextView textCount;
        ImageView trophyView;


        public MyViewHolder(View itemView) {
            super(itemView);
            this.textAchievementName = (TextView) itemView.findViewById(R.id.textAchievementName);
            this.textAchieved = (TextView) itemView.findViewById(R.id.textAchieved);
            this.textNeeded = (TextView) itemView.findViewById(R.id.textNeeded);
            this.textCount = (TextView) itemView.findViewById(R.id.textCount);
            this.trophyView = (ImageView) itemView.findViewById(R.id.trophyView);

        }
    }

    public AchievementAdapter(ArrayList<AchievementDataModel> data) {
        this.dataSet = data;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_achievements, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int listPosition) {
        TextView textAchievementName = holder.textAchievementName;
        TextView textAchieved = holder.textAchieved;
        TextView textNeeded = holder.textNeeded;
        TextView textCount = holder.textCount;
        ImageView trophyView = holder.trophyView;

        textAchievementName.setText(dataSet.get(listPosition).getName());
        if (dataSet.get(listPosition).isAchieved()){
            textAchieved.setText("Status: Completed!");
        } else {
            textAchieved.setText("Status: Incomplete");
        }

        //Todo: add better descriptions of the tasks each achievement requires.
        // Maybe have them colored when completed for feedback
        textNeeded.setText(Integer.toString(dataSet.get(listPosition).getNeeded().size()) + " tasks");
        textCount.setText("Progress: " + Integer.toString(dataSet.get(listPosition).getCount())
                + "/" + Integer.toString(dataSet.get(listPosition).getNeeded().size()) + " completed");
        trophyView.setImageDrawable(dataSet.get(listPosition).getTrophy());

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
