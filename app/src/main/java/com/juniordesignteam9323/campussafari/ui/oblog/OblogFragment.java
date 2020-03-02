package com.juniordesignteam9323.campussafari.ui.oblog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.juniordesignteam9323.campussafari.R;
import com.juniordesignteam9323.campussafari.Wildlife;

import java.util.Comparator;

public class OblogFragment extends Fragment {

    private OblogViewModel oblogViewModel;

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
    // Sorts wildlife alphabetically by common name
    public class soryByCommonName implements Comparator {
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
    public class soryByScientificName implements Comparator {
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
    public class soryByTaxon implements Comparator {
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
    public class soryByLevel implements Comparator {
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
    public class soryByPoints implements Comparator {
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
}