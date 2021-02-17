package com.example.parkingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;

import com.example.parkingapp.R;
import com.example.parkingapp.model.Parking;
import com.example.parkingapp.viewmodels.ParkingViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

//Student ID - 101334143
//Student Name - Pinalben Patel

public class ParkingDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtBuildingCode, txtCarPlateNo, txtHostno, txtAddress, txtParkingDate, txtHours;
    private Button btnViewLocation;
    private String parkingID, userID;
    private ParkingViewModel parkingViewModel;
    private Parking parkingData;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_detail);

        this.parkingViewModel = ParkingViewModel.getInstance();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Parking Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txtBuildingCode = findViewById(R.id.tvBuildingCode);
        txtAddress = findViewById(R.id.tvAddress);
        txtCarPlateNo = findViewById(R.id.tvCarPlateNo);
        txtParkingDate = findViewById(R.id.tvParkingDate);
        txtHostno = findViewById(R.id.tvSuiteHost);
        txtHours = findViewById(R.id.tvHours);
        btnViewLocation = findViewById(R.id.btnViewLoc);
        btnViewLocation.setOnClickListener(this);
        Intent intent = getIntent();

        parkingID = intent.getStringExtra("id");
        userID = intent.getStringExtra("userID");
        // Log.e("ID", "parking id in detail: " + parkingID);
        //Log.e("userID", "userID: " + userID);
        parkingViewModel.getEachParking(userID, parkingID);

        //getting updated data for selected parking
        this.parkingViewModel.getParkingRepository().eachParking.observe(this, new Observer<Parking>() {
            @Override
            public void onChanged(Parking parking) {
                // Log.e("parking data get", "data: " + parking.toString());
                parkingData = parking;
                txtBuildingCode.setText(parking.getBuildingCode());
                txtCarPlateNo.setText("Car Plate No : " + parking.getCarPlateNo());
                txtHostno.setText(parking.getNoOfHost());
                String strDate = dateFormat.format(parking.getParkingDate());
                txtParkingDate.setText((strDate));
                txtHours.setText("You can park your car till " + parking.getHoursToPark());
                txtAddress.setText(parking.getStreetAddress());
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view != null) {
            Intent intent = new Intent(this, MapsActivity.class);
            intent.putExtra("latitude", parkingData.getLatitude());
            intent.putExtra("longitude", parkingData.getLongitude());
            intent.putExtra("streetAdd", parkingData.getStreetAddress());
            startActivity(intent);
        }
    }
}