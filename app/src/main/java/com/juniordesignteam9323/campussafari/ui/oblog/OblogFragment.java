package com.juniordesignteam9323.campussafari.ui.oblog;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.juniordesignteam9323.campussafari.DataModel;
import com.juniordesignteam9323.campussafari.R;
import com.juniordesignteam9323.campussafari.UserData;
import com.juniordesignteam9323.campussafari.Wildlife;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;

public class OblogFragment extends Fragment {

    private OblogViewModel oblogViewModel;
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<DataModel> data;
    static View.OnClickListener myOnClickListener;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        oblogViewModel =
                ViewModelProviders.of(this).get(OblogViewModel.class);

        View root = inflater.inflate(R.layout.fragment_oblog, container, false);
        final TextView textView = root.findViewById(R.id.text_oblog);
        oblogViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //setContentView(R.layout.activity_oblog);
        //myOnClickListener = new MyOnClickListener(this);
        recyclerView = (RecyclerView) getView().findViewById(R.id.rvWildlife);

        recyclerView.setHasFixedSize(true);


        //was (this) before not get Activity
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        data = new ArrayList<DataModel>();
        ArrayList<Wildlife> observed = UserData.getObLog();

        /*makes a datamodel for each animal observed, then adds to data Arraylist which is sent to
        adapter to put into Observation Log's Recycler view*/
        for (int i = 0; i < observed.size(); i++) {
            if (observed.get(i).getCaught()) {
                if (observed.get(i).getCommonName() == null) {
                    System.out.println("Common Name Null");
                }
                if (observed.get(i).getTaxon() == null) {
                    System.out.println("Taxon Null");
                }
                if (observed.get(i).getScientificName() == null) {
                    System.out.println("Sci Name Null");
                }
                if (observed.get(i).getImage_url() == null) {
                    System.out.println("URL Null");
                }
                Drawable d = urlConverter(observed.get(i).getImage_url());
                data.add(new DataModel(
                        observed.get(i).getCommonName(),
                        observed.get(i).getTaxon(),
                        observed.get(i).getScientificName(),
                        observed.get(i).getCommonName(),
                        i,
                        d
                ));
                System.out.println("Data Model:" + data.get(i).getCommonName()+ data.get(i).getTaxon() + data.get(i).getScientificName());
            }
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


    // Sorts wildlife alphabetically by common name
    public class sortByCommonName implements Comparator {
        public int compare(Object o1, Object o2) {
            Wildlife w1 = (Wildlife) o1;
            Wildlife w2 = (Wildlife) o2;
            if (w1.getCommonName().compareTo(w2.getCommonName()) > 0) {
                return 1;
            } else if (w1.getCommonName().compareTo(w2.getCommonName()) < 0) {
                return -1;
            } else {
                return 0;
            }
        }
    }
    // Sorts wildlife alphabetically by scientific name
    public class sortByScientificName implements Comparator {
        public int compare(Object o1, Object o2) {
            Wildlife w1 = (Wildlife) o1;
            Wildlife w2 = (Wildlife) o2;
            if (w1.getScientificName().compareTo(w2.getScientificName()) > 0) {
                return 1;
            } else if (w1.getScientificName().compareTo(w2.getScientificName()) < 0) {
                return -1;
            } else {
                return 0;
            }
        }
    }
    // Sorts wildlife by taxon
    public class sortByTaxon implements Comparator {
        public int compare(Object o1, Object o2) {
            Wildlife w1 = (Wildlife) o1;
            Wildlife w2 = (Wildlife) o2;
            if (w1.getTaxon().compareTo(w2.getTaxon()) > 0) {
                return 1;
            } else if (w1.getTaxon().compareTo(w2.getTaxon()) < 0) {
                return -1;
            } else {
                return 0;
            }
        }
    }
    // Sorts wildlife by level highest to lowest
    // Sorts wildlife by level highest to lowest
    public class sortByLevel implements Comparator {
        public int compare(Object o1, Object o2) {
            Wildlife w1 = (Wildlife) o1;
            Wildlife w2 = (Wildlife) o2;
            if (w1.getLevel().compareTo(w2.getLevel()) > 0) {
                return 1;
            } else if (w1.getLevel().compareTo(w2.getLevel()) < 0) {
                return -1;
            } else {
                return 0;
            }
        }
    }
    // Sorts wildlife by points worth highest to lowest
    public class sortByPoints implements Comparator {
        public int compare(Object o1, Object o2) {
            Wildlife w1 = (Wildlife) o1;
            Wildlife w2 = (Wildlife) o2;
            if (w1.getPoints().compareTo(w2.getPoints()) > 0) {
                return 1;
            } else if (w1.getPoints().compareTo(w2.getPoints()) < 0) {
                return -1;
            } else {
                return 0;
            }
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