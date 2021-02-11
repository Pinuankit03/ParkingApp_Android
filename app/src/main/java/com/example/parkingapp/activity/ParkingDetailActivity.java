package com.example.parkingapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.parkingapp.R;

public class ParkingDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtBuildingCode, txtCarPlateNo, txtHostno, txtAddress, txtParkingDate, txtHours;
    private Button btnViewLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_detail);

        txtBuildingCode = findViewById(R.id.tvBuildingCode);
        txtAddress = findViewById(R.id.tvAddress);
        txtCarPlateNo = findViewById(R.id.tvCarPlateNo);
        txtParkingDate = findViewById(R.id.tvParkingDate);
        txtHostno = findViewById(R.id.tvSuiteHost);
        txtHours = findViewById(R.id.tvHours);
        btnViewLocation = findViewById(R.id.btnViewLoc);

        btnViewLocation.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if (view != null){

        }
    }
}