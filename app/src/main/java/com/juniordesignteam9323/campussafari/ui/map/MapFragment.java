package com.juniordesignteam9323.campussafari.ui.map;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.juniordesignteam9323.campussafari.CSVParse;
import com.juniordesignteam9323.campussafari.CustomInfoWindowAdapter;
import com.juniordesignteam9323.campussafari.MainActivity;
import com.juniordesignteam9323.campussafari.R;
import com.juniordesignteam9323.campussafari.UserData;
import com.juniordesignteam9323.campussafari.Wildlife;

import java.util.ArrayList;
import java.util.Random;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.appolica.interactiveinfowindow.InfoWindowManager;
import com.appolica.interactiveinfowindow.InfoWindow;
import com.appolica.interactiveinfowindow.fragment.MapInfoWindowFragment;

public class MapFragment extends Fragment {


    MapView mMapView;
    private GoogleMap googleMap;
    private Random random;
    private ArrayList<Marker> markerList;
    private LocationManager locationManager;
    private LocationListener locationListener;

    /**
     * This is a helper method created to add all the markers to the map.
     * Done by Davis Williams
     * */
    public void setUpMarkers() {
        CSVParse parser = new CSVParse("observations-64324.csv", getActivity().getApplicationContext());

        //reads the CSV file to get scientific names, common names,
        // latitudes, longitudes, observation level and image url
        ArrayList<ArrayList<String>> data = parser.getList(new int[]{36, 37, 23, 24, 10, 13});

        ArrayList<String> scientificNames = data.get(0);
        ArrayList<String> commonNames = data.get(1);
        ArrayList<String> latitudes = data.get(2);
        ArrayList<String> longitudes = data.get(3);
        ArrayList<String> urls = data.get(5);



        // Shows the InfoWindow or hides it if it is already opened.


        for (int i = 2; i < latitudes.size(); i++) {

            //checks to make sure the latitude and longitude are valid and that the level is "research"
            if (!latitudes.get(i).equals("") && !longitudes.get(i).equals("") && data.get(4).get(i).equals("research")) {
                MarkerOptions tempMark = new MarkerOptions().position(new LatLng(Double.parseDouble(latitudes.get(i)), Double.parseDouble(longitudes.get(i))));
                tempMark.title(commonNames.get(i));
                tempMark.snippet(urls.get(i) + ",Level: " + (random.nextInt(10) + 1) + ",Scientific Name: " + scientificNames.get(i));
                Marker m = googleMap.addMarker(tempMark);
                if (m != null) {
                    markerList.add(m);
                }

                //m.setTag(new InfoWindowData());
            }

        }


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = null;
        System.out.println("starting stuff....");
        markerList = new ArrayList<>();



        // Checks if the user has given us permission to use their location - if not, we don't
        // display the map, but instead we show a page asking them to go to settings and enable
        // that permission.
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            rootView = inflater.inflate(R.layout.fragment_enablelocation, container, false);
        } else {
            rootView = inflater.inflate(R.layout.fragment_map, container, false);
            mMapView = (MapView) rootView.findViewById(R.id.mapView);
            mMapView.onCreate(savedInstanceState);

            mMapView.onResume(); // needed to get the map to display immediately

            try {
                MapsInitializer.initialize(getActivity().getApplicationContext());
            } catch (Exception e) {
                e.printStackTrace();
            }

            random = new Random();

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



                    //set up markers with custom info windows
                    CustomInfoWindowAdapter customInfoWindow = new CustomInfoWindowAdapter(getContext());
                    googleMap.setInfoWindowAdapter(customInfoWindow);
                    setUpMarkers();



                    // For zooming automatically to the location of the marker
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(33.7766, -84.3982)).zoom(12).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                    googleMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

                        @Override
                        public void onInfoWindowClick(Marker marker) {
                            Log.d("catching 1", marker.getTitle());
                            Log.d("catching 2", marker.getSnippet());
                            String[] snippets = marker.getSnippet().split(",");
                            String scientific = snippets[2];
                            String imageLink = snippets[0];
                            addToOb(marker.getTitle(), scientific, imageLink);
                        }
                    });
                }
            });

            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            Log.d("Set up location manager", "success");
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    Log.d("Location changed", location.toString());

                    //int i =0; //for testing
                    for (Marker m: markerList) {

                        if (Math.abs(m.getPosition().latitude - location.getLatitude()) < 0.001 && Math.abs(m.getPosition().longitude - location.getLongitude()) < 0.001) {
                            m.setVisible(true);

                            /*for testing purposes, delete later
                            if (i < 5) {
                                String[] snippets = m.getSnippet().split(",");
                                String scientific = snippets[2];
                                String imageLink = snippets[0];
                                addToOb(m.getTitle(), scientific, imageLink); //add this available wildlife to the observation log
                                i++;
                            }*/
                            //addToOb(m.getTitle());
                            //m.setAlpha(1);
                        } else {
                            m.setVisible(false);
                            //m.setAlpha(.5f);
                        }
                    }
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {
                    Log.d("Status changed", s);
                }

                @Override
                public void onProviderEnabled(String s) {
                    Log.d("Provider enabled", s);

                }

                @Override
                public void onProviderDisabled(String s) {
                    Log.d("Provider disabled", s);
                }
            });
        }


        return rootView;
    }

    public void addToOb(String name, String scientific, String imageLink) {
        UserData ud = (UserData) getActivity().getIntent().getSerializableExtra("USERDATA");
        UserData userData = (UserData) (getActivity().getIntent().getSerializableExtra("USERDATA"));
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Wildlife wild = new Wildlife(name);

        wild.setScientificName(scientific);
        wild.setUrl(imageLink);
        ud.addToObLog(new Wildlife(name));
        Log.d("catching 3",  ud.getObLogString());
        db.collection("userData").document(user.getEmail()).set(userData);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMapView.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMapView.onPause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMapView.onDestroy();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMapView.onLowMemory();
        }
    }
}