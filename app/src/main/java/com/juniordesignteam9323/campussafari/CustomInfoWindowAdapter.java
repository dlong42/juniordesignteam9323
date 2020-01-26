package com.juniordesignteam9323.campussafari;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.io.InputStream;
import java.net.URL;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private Context context;

    public CustomInfoWindowAdapter(Context ctx) {
        context = ctx;

    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    public Drawable loadImageFromUrl(String url) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        Log.d("url", url);
        try {
            Log.d("url", "success1");
            InputStream is = (InputStream) new URL(url).getContent();
            Log.d("url", "success2");
            Drawable d = Drawable.createFromStream(is, null);
            Log.d("url", "success3");
            return d;
        } catch (Exception e) {
            Log.d("url", e.toString());

            return null;
        }
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity)context).getLayoutInflater()
                .inflate(R.layout.custom_info_window, null);


        TextView name = view.findViewById(R.id.name);
        TextView level = view.findViewById(R.id.level);
        ImageView image = view.findViewById(R.id.pic);

        name.setText(marker.getTitle());

        String[] snippets = marker.getSnippet().split(",");

        level.setText(snippets[1] + "clearly");

        //image.setImageDrawable(this.loadImageFromUrl(snippets[0]));

        image.setImageDrawable(this.loadImageFromUrl("https://static.inaturalist.org/photos/10318980/large.jpg?1504908199"));

        return view;
    }
}
