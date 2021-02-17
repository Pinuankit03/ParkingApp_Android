# ParkingApp
Created by : Pinalben Patel

Use Cloud Firestore for data persistence in ParkingApp.
Use cases : Table - User
                  - Parking
                  
Functionality : User 
Used appropriate interface to sign-up, sign-in, sign-out,
update profile and delete account. Where requesting name, email, password, contact number and car plate number from user when they create their account.
Used Email and Password as credentials When they wants to logged in an account.

Functionality - Adding Parking
 create a new parking record with the following information.
- Building code (exactly 5 alphanumeric)
- No. of hours intended to park (1-hour or less, 4-hour, 12-hour, 24-hour)
- Car Plate Number (min 2, max 8 alphanumeric)
- Suit no. of host (min 2, max 5 alphanumeric)
- Parking location (street address, lat and lng)
- date and time of parking (use system date)
For Parking location there is two options for choose location:

- enter street name [In this case the app should obtain location coordinates using geocoding]
- use current location of the device [In this case the app should use reverse geocoding to obtain
street address]

Functionality - View Parking
This facility will allow the user to view the list of all the parking they have made. displaying the list with most recent parking first.
displaying the detail view of each parking when user taps on any item of the list. When displaying detail view, display all the information about the parking in appropriate format. In the detail view of parking, 
allowed the user to view their parking location on map.
