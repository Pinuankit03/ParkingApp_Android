package com.example.parkingapp.fragment;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.parkingapp.R;
import com.example.parkingapp.common.LocationManager;
import com.example.parkingapp.common.PreferenceSettings;
import com.example.parkingapp.model.Parking;
import com.example.parkingapp.viewmodels.ParkingViewModel;
import com.example.parkingapp.viewmodels.UserViewModel;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.model.LatLng;

import java.util.Date;
import java.util.Locale;

//Student ID - 101334143
//Student Name - Pinalben Patel

public class AddParkingFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private final String TAG = this.getClass().getCanonicalName();
    private EditText edCarPlateNo, edBuildingCode, edHostNo, edParkingAdd;
    private Button btnAddParking, btnCurrentLoc;
    private ParkingViewModel parkingViewModel;
    private UserViewModel userViewModel;
    private String userID;
    private Spinner spinnerHour;
    private String selectedHours;
    public LocationManager locationManager;
    private LocationCallback locationCallback;
    private LatLng currentLocation;
    private PreferenceSettings mPreferenceSettings;
    private boolean isCurrentLoc = false;
    Address address = new Address(Locale.getDefault());

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_add_parking, container, false);
        this.parkingViewModel = ParkingViewModel.getInstance();
        this.userViewModel = UserViewModel.getInstance();
        mPreferenceSettings = new PreferenceSettings(getActivity());

        //  this.userID = this.userViewModel.getUserRepository().loggedInUserID.getValue();
        this.userID = mPreferenceSettings.getUserID();
        // Log.d("userID", userID);

        edCarPlateNo = root.findViewById(R.id.edCarPlateNo);
        edBuildingCode = root.findViewById(R.id.edBuildingCode);
        edHostNo = root.findViewById(R.id.edNoOfHost);
        edParkingAdd = root.findViewById(R.id.edParkingAddress);
        btnCurrentLoc = root.findViewById(R.id.btnCurrentLoc);
        btnAddParking = root.findViewById(R.id.btnAddParking);
        spinnerHour = root.findViewById(R.id.spinnerHours);

        btnCurrentLoc.setOnClickListener(this);
        btnAddParking.setOnClickListener(this);
        spinnerHour.setOnItemSelectedListener(this);

        this.locationManager = LocationManager.getInstance();
        this.locationManager.checkPermissions(getActivity());

        return root;
    }

    @Override
    public void onClick(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.btnAddParking: {
                    if (validateData()) {
                        addParking();
                        Navigation.findNavController(getView()).popBackStack();
                    }
                    break;
                }
                case R.id.btnCurrentLoc: {
                    if (this.locationManager.locationPermissionGranted) {
                        locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                if (locationResult == null) {
                                    return;
                                }
                                for (Location loc : locationResult.getLocations()) {
                                    //  Log.e(TAG, "Lat : " + loc.getLatitude() + "\nLng : " + loc.getLongitude());
                                    currentLocation = new LatLng(loc.getLatitude(), loc.getLongitude());
                                    isCurrentLoc = true;
                                    address = locationManager.getAddressFromLocation(getActivity(), currentLocation);
                                    edParkingAdd.setText(address.getAddressLine(0));

                                }
                            }
                        };

                        this.locationManager.requestLocationUpdates(getActivity(), this.locationCallback);
                    }
                    break;
                }
                default:
                    break;
            }
        }
    }

    private void addParking() {
        if (!isCurrentLoc) {
            if (this.locationManager.locationPermissionGranted) {
                currentLocation = locationManager.getLocationFromAddress(getActivity(), this.edParkingAdd.getText().toString());
            }
        }
        // Log.e(TAG, "Lat : " + currentLocation.latitude + "\nLng : " + currentLocation.longitude);
        Parking newParking = new Parking(edBuildingCode.getText().toString(),
                edCarPlateNo.getText().toString(),
                selectedHours,
                currentLocation.latitude, currentLocation.longitude, edHostNo.getText().toString(),
                new Date(), edParkingAdd.getText().toString());

        //save todo to db
        parkingViewModel.addParkingItem(userID, newParking);
        Toast.makeText(getActivity(), "Parking booked Successfully.", Toast.LENGTH_LONG).show();

    }

    private Boolean validateData() {
        if (this.edBuildingCode.getText().toString().isEmpty() || edBuildingCode.getText().toString().length() != 5) {
            this.edBuildingCode.setError("Please enter exact 5 character of building code.");
            return false;
        }

        if (this.edCarPlateNo.getText().toString().isEmpty() || edCarPlateNo.getText().toString().length() < 2 || edCarPlateNo.getText().toString().length() > 8) {
            this.edBuildingCode.setError("Please enter minimum 2 OR maximum 8 character of car plate no.");
            return false;
        }
        if (this.edHostNo.getText().toString().isEmpty() || edHostNo.getText().toString().length() < 2 || edHostNo.getText().toString().length() > 5) {
            this.edHostNo.setError("Please enter minimum 2 OR maximum 5 character of car plate no.");
            return false;
        }
        if (this.edParkingAdd.getText().toString().isEmpty()) {
            this.edParkingAdd.setError("Please enter Parking address.");
            return false;
        }
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        selectedHours = (String) adapterView.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == this.locationManager.LOCATION_PERMISSION_REQUEST_CODE) {
            this.locationManager.locationPermissionGranted = (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED);

            if (this.locationManager.locationPermissionGranted) {
                //start receiving location and display that on screen
                Log.e(TAG, "LocationPermissionGranted " + this.locationManager.locationPermissionGranted);
            }
            return;
        }
    }


}