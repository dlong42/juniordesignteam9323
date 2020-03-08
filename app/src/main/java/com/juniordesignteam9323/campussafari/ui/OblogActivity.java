package com.juniordesignteam9323.campussafari.ui;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.card.MaterialCardView;
import com.juniordesignteam9323.campussafari.DataModel;
import com.juniordesignteam9323.campussafari.R;
import com.juniordesignteam9323.campussafari.UserData;
import com.juniordesignteam9323.campussafari.Wildlife;
import com.juniordesignteam9323.campussafari.ui.oblog.CustomAdapter;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class OblogActivity extends AppCompatActivity {
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<DataModel> data;
    static View.OnClickListener myOnClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oblog);
        //myOnClickListener = new MyOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.rvWildlife);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        data = new ArrayList<DataModel>();
        ArrayList<Wildlife> observed = UserData.getObLog();

        /*makes a datamodel for each animal observed, then adds to data Arraylist which is sent to
        adapter to put into Observation Log's Recycler view*/
        for (int i = 0; i < observed.size(); i++) {
            Drawable d = urlConverter(observed.get(i).getUrl());
            data.add(new DataModel(
                    observed.get(i).getCommonName(),
                    observed.get(i).getTaxon(),
                    observed.get(i).getScientificName(),
                    observed.get(i).getCommonName(),
                    i,
                    d
            ));
        }
        adapter = new CustomAdapter(data);
        recyclerView.setAdapter(adapter);
    }

    public Drawable urlConverter(String url){
        try{
        InputStream is = (InputStream) new URL(url).getContent();
        Drawable d = Drawable.createFromStream(is, null);
        return d;
        } catch (Exception e) {
            Log.d("url", e.toString());
            return null;
        }
    }

    /*public static class MyOnClickListener implements View.OnClickListener {

        private final Context context;

        private MyOnClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            openItem(v);
        }

        private void openItem(View v) {
            int selectedItemPosition = recyclerView.getChildPosition(v);
            RecyclerView.ViewHolder viewHolder
                    = recyclerView.findViewHolderForPosition(selectedItemPosition);
            TextView textViewName
                    = (TextView) viewHolder.itemView.findViewById(R.id.textViewName);
            String selectedNickname = (String) textViewName.getText();
            int selectedItemId = -1;
            for (int i = 0; i < MyData.nicknameArray.length; i++) {
                if (selectedNickname.equals(MyData.nicknameArray[i])) {
                    selectedItemId = MyData.id_[i];
                }
            }
        }
    } */
}

