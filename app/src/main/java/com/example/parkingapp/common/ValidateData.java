package com.example.parkingapp.common;

import android.util.Patterns;

//Student ID - 101334143
//Student Name - Pinalben Patel

public class ValidateData {

    static String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public static boolean isValidEmailId(String email) {
        if (email.matches(emailPattern)) {
            return true;
        } else
        {
            return false;
        }
    }
    public static boolean isValidPhoneNumber(String phone) {

        if (!phone.trim().equals("") && phone.length() >= 10) {
            return Patterns.PHONE.matcher(phone).matches();
        }
        return false;
    }
}
