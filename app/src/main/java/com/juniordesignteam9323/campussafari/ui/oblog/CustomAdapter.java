package com.juniordesignteam9323.campussafari.ui.oblog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.juniordesignteam9323.campussafari.R;


import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private ArrayList<DataModel> dataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        TextView textViewNickname;
        TextView textViewTaxon;
        ImageView imageViewIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewNickname = (TextView) itemView.findViewById(R.id.textViewNickname);
            this.textViewTaxon = (TextView) itemView.findViewById(R.id.textViewTaxon);
            this.imageViewIcon = (ImageView) itemView.findViewById(R.id.imageView);

        }
    }

    public CustomAdapter(ArrayList<DataModel> data) {
        this.dataSet = data;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_oblog, parent, false);

        //view.setOnClickListener(OblogFragment.myOnClickListener);

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
        //Wildlife type = dataSet.get(listPosition).getType();

        textViewNickname.setText(dataSet.get(listPosition).getNickname());
        textViewTaxon.setText(dataSet.get(listPosition).getTaxon());
        imageView.setImageDrawable(dataSet.get(listPosition).getImage());
        //holder.cardView.setOnClickListener(new View.OnClickListener() {
            /*@Override
            public void onClick(final View view) {
                Intent intent = new Intent(view.getContext(), PasswordActivity.class);
                ////itemView.getContext().startActivity(new Intent(itemView.getContext(), WildlifeActivity.class));
                //Wildlife toSee = dataSet.get(listPosition).getType();
                //intent.putExtra("WILDLIFE", toSee);
                view.getContext().startActivity(intent);
                //Intent intent = new Intent(getActivity(), WildlifeActivity.class);
            }*/
        //});
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}