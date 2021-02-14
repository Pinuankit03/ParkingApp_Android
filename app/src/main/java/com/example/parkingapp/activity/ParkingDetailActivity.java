package com.example.parkingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.parkingapp.R;
import com.example.parkingapp.model.Parking;
import com.example.parkingapp.viewmodels.ParkingViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ParkingDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtBuildingCode, txtCarPlateNo, txtHostno, txtAddress, txtParkingDate, txtHours;
    private Button btnViewLocation;
    private String parkingID, userID;
    private ParkingViewModel parkingViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_detail);

        this.parkingViewModel = ParkingViewModel.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");


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
        Log.e("ID", "parking id in detail: " + parkingID);
        Log.e("userID", "userID: " + userID);
        parkingViewModel.getEachParking(userID, parkingID);
        this.parkingViewModel.getParkingRepository().eachParking.observe(this, new Observer<Parking>() {
            @Override
            public void onChanged(Parking parking) {
                Log.e("parking data get", "data: " + parking.toString());
                txtBuildingCode.setText(parking.getBuildingCode());
                txtCarPlateNo.setText(parking.getCarPlateNo());
                txtHostno.setText(parking.getNoOfHost());
                String strDate = dateFormat.format(parking.getParkingDate());
                txtParkingDate.setText((strDate));
                txtHours.setText("You can park only " + parking.getHoursToPark());
                txtAddress.setText(parking.getStreetAddress());
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view != null){

        }
    }
}