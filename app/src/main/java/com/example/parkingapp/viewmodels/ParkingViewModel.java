package com.example.parkingapp.viewmodels;

import androidx.lifecycle.ViewModel;

import com.example.parkingapp.model.Parking;
import com.example.parkingapp.repositories.ParkingRepository;

public class ParkingViewModel extends ViewModel {
    private final String TAG = this.getClass().getCanonicalName();
    private static final ParkingViewModel ourInstance = new ParkingViewModel();
    private final ParkingRepository parkingRepository = new ParkingRepository();

    public static ParkingViewModel getInstance(){
        return ourInstance;
    }

    public ParkingRepository getParkingRepository(){
        return parkingRepository;
    }

    private ParkingViewModel() {
    }

    public void addParkingItem(String userID, Parking parking) {
        this.parkingRepository.addParkingItem(userID, parking);
    }

    public void getAllParking(String userID) {
        this.parkingRepository.getAllParking(userID);
    }

    public void getEachParking(String userId, String parkingID) {
        this.parkingRepository.getEachParking(userId, parkingID);
    }


}
