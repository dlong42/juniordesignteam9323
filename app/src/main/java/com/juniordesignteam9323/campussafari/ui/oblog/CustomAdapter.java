package com.juniordesignteam9323.campussafari.ui.oblog;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.auth.data.model.User;
import com.juniordesignteam9323.campussafari.MainActivity;
import com.juniordesignteam9323.campussafari.R;
import com.juniordesignteam9323.campussafari.UserData;
import com.juniordesignteam9323.campussafari.Wildlife;
import com.juniordesignteam9323.campussafari.ui.PasswordActivity;


import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private static ArrayList<DataModel> dataSet;
    private static UserData userData;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        TextView textViewNickname;
        TextView textViewTaxon;
        ImageView imageViewIcon;
        Wildlife type;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewNickname = (TextView) itemView.findViewById(R.id.textViewNickname);
            this.textViewTaxon = (TextView) itemView.findViewById(R.id.textViewTaxon);
            this.imageViewIcon = (ImageView) itemView.findViewById(R.id.imageView);

            // Navigate to corresponding wildlife page when view is clicked
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    //  perform your action here
                    Intent i = new Intent(v.getContext(), WildlifeActivity.class);
                    int pos = getAdapterPosition();
                    Wildlife w = dataSet.get(pos).getType();
                    i.putExtra("WILDLIFE", w);
                    i.putExtra("USERDATA", userData);
                    v.getContext().startActivity(i);
                }
            });

        }
    }

    public CustomAdapter(ArrayList<DataModel> data, UserData userData) {
        this.dataSet = data;
        this.userData = userData;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_oblog, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
        //CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_oblog, parent, false);
        //return new MyViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int listPosition) {
        TextView textViewNickname = holder.textViewNickname;
        TextView textViewTaxon = holder.textViewTaxon;
        ImageView imageView = holder.imageViewIcon;
//        Wildlife type = dataSet.get(listPosition).getType();

        textViewNickname.setText(dataSet.get(listPosition).getNickname());
        textViewTaxon.setText(dataSet.get(listPosition).getTaxon());
        imageView.setImageDrawable(dataSet.get(listPosition).getImage());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}