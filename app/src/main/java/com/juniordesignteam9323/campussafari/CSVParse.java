package com.juniordesignteam9323.campussafari;

import android.content.Context;
import android.util.Log;

import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
public class CSVParse {
    CSVReader reader;


    //sets up a reader at the top of the CSV file
    public CSVParse(String file, Context context){
        try {
            reader = new CSVReader(new InputStreamReader(context.getAssets().open(file)));
        } catch (IOException ex) {
            Log.d("myTag", ex.toString());
        }
    }


    //can only be used once per file, give a list of the columns of the csv file you want and it will
    //return an array
    public ArrayList<ArrayList<String>> getList(int[] ns) {
        ArrayList<ArrayList<String>> toReturn = new ArrayList<ArrayList<String>>();
        for (int i = 0; i < ns.length; i++) {
            toReturn.add(new ArrayList<String>());
        }
        String[] nextLine;
        System.out.println("getList n: " + ns);
        try {
            while ((nextLine = reader.readNext()) != null) {
                // nextLine[] is an array of values from the line
                for (int i = 0; i < ns.length; i++) {
                    toReturn.get(i).add(nextLine[ns[i]]);
                }

            }
        } catch (IOException ex) {
            Log.d("myTag", ex.toString());
        }
        System.out.println("data 0: " + toReturn.get(0).get(0));
        System.out.println("data 1: " + toReturn.get(1).get(0));
        System.out.println("data 2: " + toReturn.get(2).get(0));
        System.out.println("data 3: " + toReturn.get(3).get(0));

        return toReturn;
    }

}


