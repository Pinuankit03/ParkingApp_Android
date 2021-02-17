package com.example.parkingapp.activity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.example.parkingapp.R;
import com.example.parkingapp.common.LocationManager;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

//Student ID - 101334143
//Student Name - Pinalben Patel

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private final String TAG = this.getClass().getCanonicalName();
    private final Float DEFAULT_ZOOM = 15.0f;
    private GoogleMap mMap;
    private Double latitude, longitude;
    private LocationManager locationManager;
    private LatLng currentLocation;
    private LocationCallback locationCallback;
    private String streetAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        Intent intent = getIntent();
        this.latitude = intent.getDoubleExtra("latitude", 0.0);
        this.longitude = intent.getDoubleExtra("longitude", 0.0);
        this.streetAdd = intent.getStringExtra("streetAdd");

        mapFragment.getMapAsync(this);

        this.locationManager = LocationManager.getInstance();
        this.locationManager.checkPermissions(this);

        if (this.locationManager.locationPermissionGranted) {
            locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    if (locationResult == null) {
                        return;
                    }
                    for (Location loc : locationResult.getLocations()) {

                        currentLocation = new LatLng(latitude, longitude);
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, DEFAULT_ZOOM));

                        // Log.e(TAG, "Lat : " + loc.getLatitude() + "\nLng : " + loc.getLongitude());
                        //Log.e(TAG, "Number of locations" + locationResult.getLocations().size());
                    }
                }
            };
            this.locationManager.requestLocationUpdates(this, locationCallback);
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        // LatLng sydney = new LatLng(-34, 151);
        LatLng latlong = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(latlong).title(streetAdd));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlong));
        if (mMap != null) {
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            this.setupGoogleMapScreenSetting(mMap);
        }
    }

    private void setupGoogleMapScreenSetting(GoogleMap gMap) {
        gMap.setBuildingsEnabled(true);
        gMap.setIndoorEnabled(true);
        gMap.setTrafficEnabled(false);

        UiSettings myUiSettings = gMap.getUiSettings();
        myUiSettings.setZoomControlsEnabled(true);
        myUiSettings.setZoomGesturesEnabled(true);
        myUiSettings.setMyLocationButtonEnabled(true);
        myUiSettings.setScrollGesturesEnabled(true);
        myUiSettings.setRotateGesturesEnabled(true);
    }
}