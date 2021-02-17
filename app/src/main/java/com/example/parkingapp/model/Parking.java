package com.example.parkingapp.model;

import java.util.Date;

//Student ID - 101334143
//Student Name - Pinalben Patel

public class Parking {
    String id = "";
    String buildingCode;
    String carPlateNo;
    String hoursToPark;
    double latitude;
    double longitude;
    String noOfHost;
    Date parkingDate;
    String streetAddress;

    public Parking() {
        this.buildingCode = "";
        this.carPlateNo = "";
        this.hoursToPark = "";
        this.latitude = 0.0;
        this.longitude = 0.0;
        this.noOfHost = "";
        this.parkingDate = new Date();
        this.streetAddress = "";
    }

    public Parking(String buildingCode, String carPlateNo, String hoursToPark, double latitude, double longitude, String noOfHost, Date parkingDate, String streetAddress) {
        this.buildingCode = buildingCode;
        this.carPlateNo = carPlateNo;
        this.hoursToPark = hoursToPark;
        this.latitude = latitude;
        this.longitude = longitude;
        this.noOfHost = noOfHost;
        this.parkingDate = parkingDate;
        this.streetAddress = streetAddress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBuildingCode() {
        return buildingCode;
    }

    public void setBuildingCode(String buildingCode) {
        this.buildingCode = buildingCode;
    }

    public String getCarPlateNo() {
        return carPlateNo;
    }

    public void setCarPlateNo(String carPlateNo) {
        this.carPlateNo = carPlateNo;
    }

    public String getHoursToPark() {
        return hoursToPark;
    }

    public void setHoursToPark(String hoursToPark) {
        this.hoursToPark = hoursToPark;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getNoOfHost() {
        return noOfHost;
    }

    public void setNoOfHost(String noOfHost) {
        this.noOfHost = noOfHost;
    }

    public Date getParkingDate() {
        return parkingDate;
    }

    public void setParkingDate(Date parkingDate) {
        this.parkingDate = parkingDate;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }
}
