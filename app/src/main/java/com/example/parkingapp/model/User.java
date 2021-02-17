package com.example.parkingapp.model;

//Student ID - 101334143
//Student Name - Pinalben Patel

public class User {

    String firstName;
    String lastName;
    String email;
    String password;
    String carPlateNo;
    String contactNo;
    boolean isActive;

    public User(){
        this.email = "";
        this.firstName = "";
        this.lastName = "";
        this.password = "";
        this.carPlateNo = "";
        this.contactNo = "";
        this.isActive = false;
    }


    public User(String firstName, String lastName, String email, String password, String confPassword, String carPlateNo, String contactNo, boolean isActive) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.carPlateNo = carPlateNo;
        this.contactNo = contactNo;
        this.isActive = isActive;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCarPlateNo() {
        return carPlateNo;
    }

    public void setCarPlateNo(String carPlateNo) {
        this.carPlateNo = carPlateNo;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", carPlateNo='" + carPlateNo + '\'' +
                ", contactNo='" + contactNo + '\'' +
                '}';
    }
}
