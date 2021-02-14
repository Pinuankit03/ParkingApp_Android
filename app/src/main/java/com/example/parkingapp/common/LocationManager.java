package com.example.parkingapp.common;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;


public class LocationManager {
    private static final LocationManager ourInstance = new LocationManager();
    public final int LOCATION_PERMISSION_REQUEST_CODE = 101;
    private final String TAG = this.getClass().getCanonicalName();
    private final String[] permissionArray = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};
    public Boolean locationPermissionGranted = false;
    MutableLiveData<Location> location = new MutableLiveData<>();
    private FusedLocationProviderClient fusedLocationProviderClient = null;
    private LocationRequest locationRequest;

    private LocationManager() {
        this.createLocationRequest();
    }

    public static LocationManager getInstance() {
        return ourInstance;
    }

    private void createLocationRequest() {
        this.locationRequest = new LocationRequest();
        this.locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        this.locationRequest.setInterval(5000); //5 seconds
    }

    public void checkPermissions(Context context) {
        this.locationPermissionGranted = (ContextCompat.checkSelfPermission(context.getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);

        Log.e(TAG, "LocationPermissionGranted " + this.locationPermissionGranted);

        if (!this.locationPermissionGranted) {
            this.requestLocationPermission(context);
        }
    }

    public void requestLocationPermission(Context context) {
        ActivityCompat.requestPermissions((Activity) context, this.permissionArray, this.LOCATION_PERMISSION_REQUEST_CODE);
    }

    public FusedLocationProviderClient getFusedLocationProviderClient(Context context) {
        if (fusedLocationProviderClient == null) {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        }

        return fusedLocationProviderClient;
    }

    @SuppressLint("MissingPermission")
    public MutableLiveData<Location> getLastLocation(Context context) {
        if (this.locationPermissionGranted) {
            try {

                this.getFusedLocationProviderClient(context)
                        .getLastLocation()
                        .addOnSuccessListener(new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location loc) {
                                if (loc != null) {
                                    location.setValue(loc);
                                    Log.e(TAG, "Last Location ---- Lat : " + location.getValue().getLatitude() +
                                            " Lng : " + location.getValue().getLongitude());
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e(TAG, e.toString());
                                Log.e(TAG, e.getLocalizedMessage());
                            }
                        });

            } catch (Exception ex) {
                Log.e(TAG, ex.getLocalizedMessage());
                return null;
            }

            return this.location;

        } else {
            //explain the user why location is unavailable
            //request for permissions
        }

        return null;
    }

    @SuppressLint("MissingPermission")
    public void requestLocationUpdates(Context context, LocationCallback locationCallback) {
        if (this.locationPermissionGranted) {
            try {
                this.getFusedLocationProviderClient(context).requestLocationUpdates(this.locationRequest, locationCallback, Looper.getMainLooper());
            } catch (Exception ex) {
                Log.e(TAG, ex.toString());
                Log.e(TAG, ex.getLocalizedMessage());
            }
        }
    }

    public void stopLocationUpdates(Context context, LocationCallback locationCallback) {
        try {
            this.getFusedLocationProviderClient(context).removeLocationUpdates(locationCallback);
        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
            Log.e(TAG, ex.getLocalizedMessage());
        }
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }
}
