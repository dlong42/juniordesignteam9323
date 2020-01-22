package com.juniordesignteam9323.campussafari;

import android.content.Context;


import com.opencsv.CSVReader;
import java.io.IOException;
import java.io.*;
import android.util.Log;

import java.util.*;
public class CSVParse {
    CSVReader reader;

    public CSVParse(String file, Context context){
        try {
            reader = new CSVReader(new InputStreamReader(context.getAssets().open(file)));
        } catch (IOException ex) {
            Log.d("myTag", ex.toString());
        }
    }

    public ArrayList<String> getList(int n) {
        ArrayList<String> toReturn = new ArrayList<String>();
        String[] nextLine;
        try {
            while ((nextLine = reader.readNext()) != null) {
                // nextLine[] is an array of values from the line
                toReturn.add(nextLine[n]);
            }
        } catch (IOException ex) {
            Log.d("myTag", ex.toString());
        }
        return toReturn;
    }

}


