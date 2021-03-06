package com.juniordesignteam9323.campussafari.ui.map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.juniordesignteam9323.campussafari.CSVParse;
import com.juniordesignteam9323.campussafari.CustomInfoWindowAdapter;
import com.juniordesignteam9323.campussafari.LevelUpDialog;
import com.juniordesignteam9323.campussafari.MainActivity;
import com.juniordesignteam9323.campussafari.R;
import com.juniordesignteam9323.campussafari.UserData;
import com.juniordesignteam9323.campussafari.Wildlife;
import com.juniordesignteam9323.campussafari.ui.oblog.WildlifeActivity;

import java.util.ArrayList;
import java.util.Random;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

/**
 * This is the main fragment that displays the map and allows users to log wildlife.
 */
public class MapFragment extends Fragment {


    MapView mMapView;
    private GoogleMap googleMap;
    private UserData userData;
    private Random random;
    private ArrayList<Marker> markerList;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Wildlife wildlifeToBeAdded;
    private static final int DIALOG_FRAGMENT_REQUEST_CODE = 1;

    /**
     * This is a helper method created to add all the markers to the map.
     *
     * */
    public void setUpMarkers() {
        UserData ud = (UserData) getActivity().getIntent().getSerializableExtra("USERDATA");
        UserData userData = (UserData) (getActivity().getIntent().getSerializableExtra("USERDATA"));
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        //reads CSV to get all wildife
        CSVParse parser = new CSVParse("wildlifeDB.csv", getActivity().getApplicationContext());

        ArrayList<ArrayList<String>> data = parser.getList(new int[]{0, 3, 4, 5, 6, 7, 9, 10, 8, 11, 12});
        ArrayList<String> commonNames = data.get(5);
        ArrayList<String> scientificNames = data.get(4);
        ArrayList<String> taxons = data.get(8);
        ArrayList<String> levels = data.get(6);
        ArrayList<String> points = data.get(7);
        ArrayList<String> latitudes = data.get(2);
        ArrayList<String> longitudes = data.get(3);
        ArrayList<String> urls = data.get(1);
        ArrayList<String> ids = data.get(0);
        ArrayList<String> funFacts = data.get(9);
        ArrayList<String> citations = data.get(10);

        // Shows the InfoWindow or hides it if it is already opened.


        for (int i = 2; i < latitudes.size(); i++) {

            //checks to make sure the latitude and longitude are valid and that the level is "research"
            if (!latitudes.get(i).equals("") && !longitudes.get(i).equals("")) {
                double lat = Double.parseDouble(latitudes.get(i));
                double lon = Double.parseDouble(longitudes.get(i));
                int id = Integer.parseInt(ids.get(i));
                Wildlife tempWildlife = new Wildlife(commonNames.get(i), scientificNames.get(i), taxons.get(i), levels.get(i), points.get(i), urls.get(i), lat, lon, id, funFacts.get(i), citations.get(i));
                tempWildlife.setCaught(ud.isCaught(tempWildlife));
                MarkerOptions tempMark = new MarkerOptions().position(new LatLng(lat, lon));

                tempMark.title(tempWildlife.getCommonName());
                tempMark.snippet(tempWildlife.getImage_url() + ",Level: " + tempWildlife.getLevel());
                tempMark.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));

                Marker m = googleMap.addMarker(tempMark);
                if (tempWildlife.getCaught()) {
                    m.setAlpha(.25f);
                }
                m.setTag(tempWildlife);
                if (m != null) {
                    markerList.add(m);
                }

                //m.setTag(new InfoWindowData());
            }

        }

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

            //Sets up the log button and hides it.
            final FloatingActionButton catch_button = rootView.findViewById(R.id.catch_button);
            catch_button.hide();

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
                    try {
                        setUpMarkers();
                    } catch (NullPointerException e) {
                        Log.d("Exception", "whoops");
                    }


                    // For zooming automatically to the location of the marker
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(33.7766, -84.3982)).zoom(12).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                    //When a marker is clicked, the Log button is shown, unless the wildlife is caught.
                    googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {
                            final Marker mapMarker = marker;
                            wildlifeToBeAdded = (Wildlife) mapMarker.getTag();
                            if (!wildlifeToBeAdded.getCaught()){
                                catch_button.show();
                            }

                            //Log wildlife when this is hit
                            catch_button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    String[] snippets = mapMarker.getSnippet().split(",");
                                    String image_url = snippets[0];



                                    //wildlifeToBeAdded = (Wildlife) mapMarker.getTag();
                                    openLogWildlifeDialog();


                                    mapMarker.setAlpha(.25f);
                                    catch_button.hide();
                                }
                            });
                            return false;
                        }
                    });

                    //Hide the log button
                    googleMap.setOnInfoWindowCloseListener(new GoogleMap.OnInfoWindowCloseListener() {
                        @Override

                        public void onInfoWindowClose(Marker marker) {
                            catch_button.hide();
                        }
                    });
                }
            });

            //Handles user location changes
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5, 0, new LocationListener() {
                @SuppressLint("MissingPermission")
                @Override
                public void onLocationChanged(Location location) {
                    if((location.getLatitude() > 33.781) || (location.getLatitude() < 33.771) || (location.getLongitude() < -84.407) || (location.getLongitude() > -84.392)) {
                        userData.comeBack();
                    }
                    for (Marker m: markerList) {
                        //Makes only nearby markers of the correct level visible
                        if (Math.abs(m.getPosition().latitude - location.getLatitude()) < 0.001
                                && Math.abs(m.getPosition().longitude - location.getLongitude()) < 0.001
                                && Integer.parseInt(((Wildlife) m.getTag()).getLevel()) <= userData.getLevel()) {
                            m.setVisible(true);

                        } else {
                            m.setVisible(false);
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


    // Opens the log wildlife dialog, which appears when a user presses the "Log" button
    public void openLogWildlifeDialog() {
        LogWildlifeDialog logWildlifeDialog = new LogWildlifeDialog();
        // Necessary to pass data from the dialog to the MapFragment
        logWildlifeDialog.setTargetFragment(this, DIALOG_FRAGMENT_REQUEST_CODE);
        logWildlifeDialog.show(getFragmentManager(), "log wildlife dialog");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if( resultCode != Activity.RESULT_OK ) {
            return;
        }
        // Gets the wildlife nickname data from the log wildlife dialog and sets it
        if( requestCode == DIALOG_FRAGMENT_REQUEST_CODE ) {
            String nickname = data.getStringExtra("nickname");
            // If the user did not input a nickname, the wildlife's nickname is set to its common
            // name by default
            if (nickname.length() == 0) {
                nickname = wildlifeToBeAdded.getCommonName();
            }
            wildlifeToBeAdded.setNickname(nickname);
            addToObInit(wildlifeToBeAdded);
        }
    }

    //adds all wildlife to the observation log initially, then when they are actually logged their caught variable changes
    public void addToObInit(Wildlife toAdd) {
        UserData ud = (UserData) getActivity().getIntent().getSerializableExtra("USERDATA");
        UserData userData = (UserData) (getActivity().getIntent().getSerializableExtra("USERDATA"));
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        boolean levelUpdate = false;
        boolean fourPoint =userData.achievementBeenDisplayed(0);
        boolean taxa = userData.achievementBeenDisplayed(1);
        boolean aFor = userData.achievementBeenDisplayed(2);
        boolean come = userData.achievementBeenDisplayed(3);
        boolean funGuy = userData.achievementBeenDisplayed(4);
        if(toAdd.catchWildlife()) {
            userData.addToObLog(toAdd);
            levelUpdate = userData.updatePoints(Integer.parseInt(toAdd.getPoints()));
        } else {
            Log.d("catching 4a",  "already caught");
        }
        int comeCurrCount = userData.getAchievement(3).getCount();
        Toast toast;
        Context context = getContext();
        CharSequence frontText = "Congratulations! You have gained the ";
        CharSequence backText = " achievement.";
        int duration = Toast.LENGTH_LONG;
        if(!fourPoint && userData.isAchieved(0)) {
            Toast.makeText(getContext(), frontText+ userData.getAchievements().get(0).getName() + backText, duration).show();
            userData.setDisplayed(0, true);
        }
        if(!taxa && userData.isAchieved(1)) {
            Toast.makeText(getContext(), frontText+ userData.getAchievements().get(1).getName() + backText, duration).show();
            userData.setDisplayed(1, true);
        }
        if(!aFor && userData.isAchieved(2)) {
            Toast.makeText(getContext(), frontText+ userData.getAchievements().get(2).getName() + backText, duration).show();
            userData.setDisplayed(2, true);
        }
        if(!come && userData.isAchieved(3) && (comeCurrCount == 1)) {
            Toast.makeText(getContext(), frontText+ userData.getAchievements().get(3).getName() + backText, duration).show();
            userData.setDisplayed(3, true);
        }
        if(!funGuy && userData.isAchieved(4)) {
            Toast.makeText(getContext(), frontText+ userData.getAchievements().get(4).getName() + backText, duration).show();
            userData.setDisplayed(4, true);
        }
        MainActivity main = ((MainActivity) getActivity());

        TextView levelView = main.getLevelView();
        TextView pointsView = main.getPointsView();
        ProgressBar xpBar = main.getXpBar();
        levelView.setText("Level: " + userData.getLevel());
        pointsView.setText("Points: " + userData.getPoints() + "/" + UserData.levelThreshold(userData.getLevel()));
        if (levelUpdate) {
            xpBar.setMax(UserData.levelThreshold(userData.getLevel()));
        }
        xpBar.setProgress(userData.getPoints());

        main.setLevelView(levelView);
        main.setPointsView(pointsView);
        main.setXpBar(xpBar);

        db.collection("userData").document(user.getEmail()).set(userData);
        // Navigates to WildlifeActivity, passing in the index of the wildlife in Oblog
        Intent intent = new Intent(getActivity(), WildlifeActivity.class);
        intent.putExtra("WILDLIFE", toAdd);
        intent.putExtra("USERDATA", userData);
        if (!levelUpdate) {
            startActivity(intent);
        } else {
            DialogFragment levelUp = new LevelUpDialog(intent);
            levelUp.show(getFragmentManager(), "levelUp");
        }

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

    public Wildlife getWildlifeToBeAdded() {
        return wildlifeToBeAdded;
    }
}