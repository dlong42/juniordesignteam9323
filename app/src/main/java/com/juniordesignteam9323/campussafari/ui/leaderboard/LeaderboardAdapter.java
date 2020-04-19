package com.juniordesignteam9323.campussafari.ui.leaderboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.juniordesignteam9323.campussafari.R;
import java.util.ArrayList;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.MyViewHolder> {

    private ArrayList<LeaderboardDataModel> dataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        TextView textPlace;
        TextView textLeaderName;
        TextView textPoints;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textPlace = (TextView) itemView.findViewById(R.id.textPlace);
            this.textLeaderName = (TextView) itemView.findViewById(R.id.textLeaderName);
            this.textPoints = (TextView) itemView.findViewById(R.id.textPoints);
        }
    }

    public LeaderboardAdapter(ArrayList<LeaderboardDataModel> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_leaderboard, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int listPosition) {
        TextView textPlace = holder.textPlace;
        TextView textLeaderName = holder.textLeaderName;
        TextView textPoints = holder.textPoints;

        textPlace.setText(Integer.toString(listPosition + 1));
        textLeaderName.setText(dataSet.get(listPosition).getName());
        textPoints.setText("Points: " + Integer.toString(dataSet.get(listPosition).getPoints()));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}