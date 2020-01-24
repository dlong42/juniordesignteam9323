package com.juniordesignteam9323.campussafari.ui.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.juniordesignteam9323.campussafari.CSVParse;
import com.juniordesignteam9323.campussafari.R;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;

public class MapFragment extends Fragment {


    MapView mMapView;
    private GoogleMap googleMap;


    public void setUpMarkers() {
        CSVParse parser = new CSVParse("observations-75146.csv", getActivity().getApplicationContext());

        ArrayList<ArrayList<String>> data = parser.getList(new int[]{36, 37, 23, 24});

        ArrayList<String> scientificNames = data.get(0);
        ArrayList<String> commonNames = data.get(1);
        ArrayList<String> latitudes = data.get(2);
        ArrayList<String> longitudes = data.get(3);

        System.out.println("" + scientificNames.size() + " " + commonNames.size() + " " + latitudes.size() + " " + longitudes.size());
        for (int i = 2; i < latitudes.size(); i++) {
            System.out.println(i + ": " + latitudes.get(i) + ", " + longitudes.get(i));

            if (!latitudes.get(i).equals("") && !longitudes.get(i).equals("")) {
                googleMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(latitudes.get(i)), Double.parseDouble(longitudes.get(i)))).title(commonNames.get(i)).snippet(scientificNames.get(i))).setVisible(true);
            }

        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        System.out.println("starting stuff....");
        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                //confine map and set zoom
                googleMap.setLatLngBoundsForCameraTarget(new LatLngBounds(new LatLng(33.7677, -84.4062),
                        new LatLng(33.7814, -84.3906)));
                googleMap.setMinZoomPreference(14.0f);

                // For showing a move to my location button
                googleMap.setMyLocationEnabled(true);

                //googleMap.addMarker(new MarkerOptions().position(new LatLng(33.7790974992, -84.3995344268)).title("Squirrel").snippet("latin squirrel")).setVisible(true);

                System.out.println("Map set up");
                setUpMarkers();
                System.out.println("done with marker setup");
                

                // For dropping a marker at a point on the Map


                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(33.7766, -84.3982)).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}