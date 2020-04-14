package com.juniordesignteam9323.campussafari.ui.oblog;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.juniordesignteam9323.campussafari.MainActivity;
import com.juniordesignteam9323.campussafari.R;
import com.juniordesignteam9323.campussafari.UserData;
import com.juniordesignteam9323.campussafari.Wildlife;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.annotation.Nullable;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OblogFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    private OblogViewModel oblogViewModel;
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<DataModel> data;
    static View.OnClickListener myOnClickListener;
    private UserData userData;
    private ArrayList<Wildlife> observed;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        userData = ((MainActivity) getActivity()).getUserData();
        observed = userData.getObLog();
        oblogViewModel = ViewModelProviders.of(this, new OblogViewModel(userData))
                .get(OblogViewModel.class);

        View root = inflater.inflate(R.layout.fragment_oblog, container, false);
        final TextView textView = root.findViewById(R.id.text_oblog);
        oblogViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        Spinner oblogSpinner = (Spinner) root.findViewById(R.id.oblog_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.sortby, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        oblogSpinner.setAdapter(adapter);
        oblogSpinner.setOnItemSelectedListener(this);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = (RecyclerView) getView().findViewById(R.id.rvWildlife);

        recyclerView.setHasFixedSize(true);


        //was (this) before not get Activity
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        //makes a datamodel for each animal observed, then adds to data Arraylist which is sent to
        //adapter to put into Observation Log's Recycler view*
        data = new ArrayList<DataModel>();
        //ArrayList<Wildlife> observed = userData.getObLog();
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
                        observed.get(i).getNickname(),
                        i,
                        d,
                        observed.get(i)
                ));
                System.out.println("Data Model:" + data.get(i).getCommonName()+ data.get(i).getTaxon() + data.get(i).getScientificName());
            }
        }
        adapter = new CustomAdapter(data, userData);
        recyclerView.setAdapter(adapter);
    }


    /** makes a datamodel for each animal observed, then adds to updatedData Arraylist
     **/
    public ArrayList<DataModel> updateData(){
        ArrayList<DataModel> updatedData = new ArrayList<>();
        for (int i = 0; i < observed.size(); i++) {
            if (observed.get(i).getCaught()) {

                //error checking
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

                //making data models of observed wildlife and loading into an arraylist
                Drawable d = urlConverter(observed.get(i).getImage_url());
                updatedData.add(new DataModel(
                        observed.get(i).getNickname(),
                        observed.get(i).getTaxon(),
                        observed.get(i).getScientificName(),
                        observed.get(i).getCommonName(),
                        i,
                        d,
                        observed.get(i)
                ));
                System.out.println("Data Model:" + updatedData.get(i).getCommonName()+ updatedData.get(i).getTaxon() + updatedData.get(i).getScientificName());
            }
        }
        return updatedData;
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

    private boolean moreThanTwo(){
        int count = 0;
        for (int i = 0; i < observed.size(); i++){
            if (observed.get(i).getCaught()){
                count++;
            }
        }
        return (count >= 2);
    }

    //oblog spinner stuff. depending on entry selected, it sorts the list, updates data and data
    // models, then calls for the adapter to refresh.
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        if (moreThanTwo()){
            System.out.println("Presort:" + observed.get(0).getCommonName() + observed.get(1).getCommonName());
            String text = adapterView.getItemAtPosition(position).toString();
            if (text.equals("Nickname")){
                SortByNickname sortAlgoNickname = new SortByNickname();
                Collections.sort(observed, sortAlgoNickname);
            }
            if (text.equals("Common Name")) {
                System.out.println("Common name clicked");
                SortByCommonName sortAlgoCommon = new SortByCommonName();
                Collections.sort(observed, sortAlgoCommon);
            } else if (text.equals("Scientific Name")) {
                SortByScientificName sortAlgoSci = new SortByScientificName();
                Collections.sort(observed, sortAlgoSci);
            } else if (text.equals("Taxon")) {
                SortByTaxon sortAlgoTaxon = new SortByTaxon();
                Collections.sort(observed, sortAlgoTaxon);
            } else if (text.equals("Level")) {
                SortByLevel sortAlgoLevel = new SortByLevel();
                Collections.sort(observed, sortAlgoLevel);
            } else if (text.equals("Points")) {
                SortByPoints sortAlgoPoints = new SortByPoints();
                Collections.sort(observed, sortAlgoPoints);
            }
            System.out.println("Resorted by " + text + ":" + observed.get(0).getCommonName() + observed.get(1).getCommonName());
            /*boolean sorted = false;
            while (!sorted) {
                for (int i = 0; i < observed.size() - 1; i++) {
                    int compareValue;
                    Wildlife w1 = observed.get(i);
                    Wildlife w2 = observed.get(i+1);
                    if (text == "Common Name"){
                        SortByCommonName sortAlgo = new SortByCommonName();
                       Collections.sort(observed, sortAlgo);
                    }
                    if (observed.get(i).)
                }
            }
            if (text == "Common Name"){
                SortbyCommonName();
            }*/
            data = updateData();
            adapter.notifyDataSetChanged();
            recyclerView.invalidate();
            recyclerView.setAdapter(new CustomAdapter(data, userData));
            //adapter = new CustomAdapter(data);
            //recyclerView.setAdapter(adapter);
            Toast.makeText(adapterView.getContext(), "Sorted by " + text, Toast.LENGTH_SHORT).show();
        } else {
            return;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    // Sorts wildlife alphabetically by nickname
    public class SortByNickname implements Comparator<Wildlife> {
        public int compare(Wildlife w1, Wildlife w2) {
            if (w1.getNickname().compareTo(w2.getNickname()) > 0) {
                return 1;
            } else if (w1.getNickname().compareTo(w2.getNickname()) < 0) {
                return -1;
            } else {
                return 0;
            }
        }
    }


    // Sorts wildlife alphabetically by common name
    public class SortByCommonName implements Comparator<Wildlife> {
        public int compare(Wildlife w1, Wildlife w2) {
            System.out.println("Sort by common name started");
            if (w1.getCommonName().compareTo(w2.getCommonName()) > 0) {
                System.out.println("Sort by common name judged as >0");
                return 1;
            } else if (w1.getCommonName().compareTo(w2.getCommonName()) < 0) {
                System.out.println("Sort by common name judged as <0");
                return -1;
            } else if (w1.getCommonName().compareTo(w2.getCommonName()) == 0){
                System.out.println("Sort by common name judged as 0");
                return 0;
            } else {
                System.out.println("Sort by common name exception");
                return 0;
            }
        }
    }
    // Sorts wildlife alphabetically by scientific name
    public class SortByScientificName implements Comparator<Wildlife> {
        public int compare(Wildlife w1, Wildlife w2) {
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
    public class SortByTaxon implements Comparator<Wildlife> {
        public int compare(Wildlife w1, Wildlife w2) {
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
    public class SortByLevel implements Comparator<Wildlife> {
        public int compare(Wildlife w1, Wildlife w2) {
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
    public class SortByPoints implements Comparator<Wildlife> {
        public int compare(Wildlife w1, Wildlife w2) {
            if (w1.getPoints().compareTo(w2.getPoints()) > 0) {
                return 1;
            } else if (w1.getPoints().compareTo(w2.getPoints()) < 0) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}