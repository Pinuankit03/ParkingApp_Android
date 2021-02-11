package com.example.parkingapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

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

import com.example.parkingapp.R;
import com.example.parkingapp.model.Parking;
import com.example.parkingapp.viewmodels.ParkingViewModel;
import com.example.parkingapp.viewmodels.UserViewModel;

import java.util.Date;

public class AddParkingFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private EditText edCarPlateNo, edBuildingCode, edHostNo, edParkingAdd;
    private Button btnAddParking, btnCurrentLoc;
    private ParkingViewModel parkingViewModel;
    private UserViewModel userViewModel;
    private String userID;
    private Spinner spinnerHour;
    private  String selectedHours;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_add_parking, container, false);
        this.parkingViewModel = ParkingViewModel.getInstance();
        this.userViewModel = UserViewModel.getInstance();
        this.userID = this.userViewModel.getUserRepository().loggedInUserID.getValue();
        Log.d("userID",userID);

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
        return root;

    }

    @Override
    public void onClick(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.btnAddParking: {
                    if (validateData()) {
                        addParking();
                    }
                }
                default:
                    break;
            }
        }
    }

    private void addParking() {

        Parking newParking = new Parking(edBuildingCode.getText().toString(),
                edCarPlateNo.getText().toString(),
                selectedHours,
                0.0, 0.0, edHostNo.getText().toString(),
                new Date(), edParkingAdd.getText().toString());

        //save todo to db
        parkingViewModel.addParkingItem(userID, newParking);
    }

    private Boolean validateData() {

        if (this.edBuildingCode.getText().toString().isEmpty() || edBuildingCode.getText().toString().length() != 5) {
            this.edBuildingCode.setError("Please enter exact 5 character of building code.");
            return false;
        }

        if (this.edCarPlateNo.getText().toString().isEmpty() || edCarPlateNo.getText().toString().length() <2 || edCarPlateNo.getText().toString().length() >8) {
            this.edBuildingCode.setError("Please enter minimum 2 OR maximum 8 character of car plate no.");
            return false;
        }
        if (this.edHostNo.getText().toString().isEmpty() || edHostNo.getText().toString().length() <2 || edHostNo.getText().toString().length() >5) {
            this.edHostNo.setError("Please enter minimum 2 OR maximum 5 character of car plate no.");
            return false;
        }
        if (this.edParkingAdd.getText().toString().isEmpty()){
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
}