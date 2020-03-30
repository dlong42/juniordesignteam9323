package com.juniordesignteam9323.campussafari.ui.map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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
import com.juniordesignteam9323.campussafari.R;
import com.juniordesignteam9323.campussafari.UserData;
import com.juniordesignteam9323.campussafari.Wildlife;
import com.juniordesignteam9323.campussafari.ui.oblog.WildlifeActivity;

import java.util.ArrayList;
import java.util.Random;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

public class MapFragment extends Fragment {


    MapView mMapView;
    private GoogleMap googleMap;
    private UserData userData;
    private Random random;
    private ArrayList<Marker> markerList;
    private LocationManager locationManager;
    private LocationListener locationListener;

    /**
     * This is a helper method created to add all the markers to the map.
     * Done by Davis Williams
     * */
    public void setUpMarkers() {
        UserData ud = (UserData) getActivity().getIntent().getSerializableExtra("USERDATA");
        UserData userData = (UserData) (getActivity().getIntent().getSerializableExtra("USERDATA"));
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //CSVParse parser = new CSVParse("observations-64324.csv", getActivity().getApplicationContext());

        //reads the CSV file to get scientific names, common names,
        // latitudes, longitudes, observation level and image url
        //ArrayList<ArrayList<String>> data = parser.getList(new int[]{36, 37, 23, 24, 10, 13});

        //ArrayList<String> scientificNames = data.get(0);
       // ArrayList<String> commonNames = data.get(1);
        //ArrayList<String> latitudes = data.get(2);
        //ArrayList<String> longitudes = data.get(3);
        //ArrayList<String> urls = data.get(5);


        CSVParse parser = new CSVParse("wildlifeDB.csv", getActivity().getApplicationContext());

        ArrayList<ArrayList<String>> data = parser.getList(new int[]{0, 3, 4, 5, 6, 7, 9, 10, 8});
        ArrayList<String> commonNames = data.get(5);
        ArrayList<String> scientificNames = data.get(4);
        ArrayList<String> taxons = data.get(8);
        ArrayList<String> levels = data.get(6);
        ArrayList<String> points = data.get(7);
        ArrayList<String> latitudes = data.get(2);
        ArrayList<String> longitudes = data.get(3);
        ArrayList<String> urls = data.get(1);
        ArrayList<String> ids = data.get(0);

        // Shows the InfoWindow or hides it if it is already opened.


        for (int i = 2; i < latitudes.size(); i++) {

            //checks to make sure the latitude and longitude are valid and that the level is "research"
            if (!latitudes.get(i).equals("") && !longitudes.get(i).equals("")) {
                double lat = Double.parseDouble(latitudes.get(i));
                double lon = Double.parseDouble(longitudes.get(i));
                int id = Integer.parseInt(ids.get(i));
                Wildlife tempWildlife = new Wildlife(commonNames.get(i), scientificNames.get(i), taxons.get(i), levels.get(i), points.get(i), urls.get(i), lat, lon, id);
                tempWildlife.setCaught(ud.isCaught(tempWildlife));
                MarkerOptions tempMark = new MarkerOptions().position(new LatLng(lat, lon));

                tempMark.title(tempWildlife.getCommonName());
                tempMark.snippet(tempWildlife.getImage_url() + ",Level: " + tempWildlife.getLevel());

                if (tempWildlife.getCaught()) {
                    tempMark.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                } else {
                    tempMark.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                }
                Marker m = googleMap.addMarker(tempMark);
                m.setTag(tempWildlife);
                if (m != null) {
                    markerList.add(m);
                }

                //m.setTag(new InfoWindowData());
            }

        }

//        db.collection("userData").document(user.getEmail()).set(userData);
//        // Navigates to WildlifeActivity, passing in the index of the wildlife in Oblog
//        Intent intent = new Intent(getActivity(), WildlifeActivity.class);
//        startActivity(intent);
    }



    @SuppressLint("MissingPermission")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = null;
        System.out.println("starting stuff....");
        markerList = new ArrayList<>();
        final UserData userData = (UserData) (getActivity().getIntent().getSerializableExtra("USERDATA"));



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
                            Log.d("catching 1", "trying to catch " + marker.getTitle() + " " + ((Wildlife)marker.getTag()).getId());
                            Log.d("catching 2", marker.getSnippet());
                            String[] snippets = marker.getSnippet().split(",");
                            String image_url = snippets[0];

                            Log.d("catching 3", "previously caught " + ((Wildlife) marker.getTag()).getCaught() + "");
                            addToObInit((Wildlife) marker.getTag());
                            Log.d("catching 5", ((Wildlife) marker.getTag()).getCaught() + "");
                            Log.d("LEveL: ", ((Wildlife) marker.getTag()).getLevel());
                            Log.d("User level: ", "" + userData.getLevel());
                            marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                        }
                    });
                }
            });

            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            Log.d("Set up location manager", "success");
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5, 0, new LocationListener() {
                @SuppressLint("MissingPermission")
                @Override
                public void onLocationChanged(Location location) {
                    Log.d("Location changed", location.toString());
                    for (Marker m: markerList) {
                        if (Math.abs(m.getPosition().latitude - location.getLatitude()) < 0.001
                                && Math.abs(m.getPosition().longitude - location.getLongitude()) < 0.001
                                && Integer.parseInt(((Wildlife) m.getTag()).getLevel()) <= userData.getLevel()) {
                            m.setVisible(true);
                            //addToOb(m.getTitle());  //add this available wildlife to the observation log
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
//    public void catchThis(Wildlife toAdd) {
//        UserData ud = (UserData) getActivity().getIntent().getSerializableExtra("USERDATA");
//        UserData userData = (UserData) (getActivity().getIntent().getSerializableExtra("USERDATA"));
//        FirebaseAuth auth = FirebaseAuth.getInstance();
//        FirebaseUser user = auth.getCurrentUser();
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//
//        toAdd.catchWildlife();
//
//        db.collection("userData").document(user.getEmail()).set(userData);
//        // Navigates to WildlifeActivity, passing in the index of the wildlife in Oblog
//        Intent intent = new Intent(getActivity(), WildlifeActivity.class);
//        intent.putExtra("WILDLIFE", toAdd);
//        startActivity(intent);
//    }

    //adds all wildlife to the observation log initially, then when they are actually logged their caught variable changes
    public void addToObInit(Wildlife toAdd) {
        UserData ud = (UserData) getActivity().getIntent().getSerializableExtra("USERDATA");
        UserData userData = (UserData) (getActivity().getIntent().getSerializableExtra("USERDATA"));
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
//      Wildlife wild = new Wildlife(name);
//      wild.setScientificName(scientific);
//      wild.setImage_url(image_url);
        if(toAdd.catchWildlife()) {
            ud.addToObLog(toAdd);
        } else {
            Log.d("catching 4a",  "already caught");
        }
        Log.d("catching 4b",  ud.getObLogString());
        db.collection("userData").document(user.getEmail()).set(userData);
        // Navigates to WildlifeActivity, passing in the index of the wildlife in Oblog
        Intent intent = new Intent(getActivity(), WildlifeActivity.class);
        intent.putExtra("WILDLIFE", toAdd);
        startActivity(intent);
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