package com.juniordesignteam9323.campussafari;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.io.InputStream;
import java.net.URL;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private Context context;
    private String wildlifeName;
    public CustomInfoWindowAdapter(Context ctx) {
        context = ctx;
    }

    //this just returns null because InfoWindowAdapter checks this method before getInfoContents
    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }


    //This method takes a url for an image and turns it into a Drawable object
    //without actually downloading the image.
    public Drawable loadImageFromUrl(String url) {
        //TODO: This is a bad way of getting the internet to work.
        // Could make the app not run at all in areas with bad internet
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
////
////        StrictMode.setThreadPolicy(policy);
////
////        try {
////            InputStream is = (InputStream) new URL(url).getContent();
////            Drawable d = Drawable.createFromStream(is, null);
////            return d;
////        } catch (Exception e) {
////            Log.d("url", e.toString());
////            return null;
////        }
        try {
            Drawable d = new RetrieveImage().execute(url).get();
            return d;
        } catch(Exception e) {
            Log.d("url_out", e.getStackTrace().toString());
            return null;
        }
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity)context).getLayoutInflater()
                .inflate(R.layout.custom_info_window, null);


        //initializes view for the InfoWindow
        TextView name = view.findViewById(R.id.name);
        TextView level = view.findViewById(R.id.level);
        ImageView image = view.findViewById(R.id.pic);
        //Button button = view.findViewById(R.id.button_id);

        wildlifeName = name.toString();

//        button.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                UserData ud = ((MainActivity) context).getUserData();
//                ud.addToObLog(new Wildlife(wildlifeName));
//                Log.d("catching", wildlifeName);
//            }
//        });

        name.setText(marker.getTitle());

        //the snippet is in the format of "url, level"
        String[] snippets = marker.getSnippet().split(",");
        level.setText(snippets[1]);
        image.setImageDrawable(this.loadImageFromUrl(snippets[0]));
        return view;
    }
}

class RetrieveImage extends AsyncTask<String, Void, Drawable> {
    protected void onPreExecute(){
        super.onPreExecute();
    }

    protected Drawable doInBackground(String... urls) {
        try {
            InputStream is = (InputStream) new URL(urls[0]).getContent();
            Drawable d = Drawable.createFromStream(is, null);
            return d;
        } catch (Exception e) {
            Log.d("url", e.toString());
            return null;
        }
    }

    protected void onPostExecute(Drawable result) {
        super.onPostExecute(result);
    }
}