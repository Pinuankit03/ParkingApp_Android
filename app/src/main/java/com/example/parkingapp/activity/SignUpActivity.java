package com.example.parkingapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.parkingapp.R;
import com.example.parkingapp.common.ValidateData;
import com.example.parkingapp.model.User;
import com.example.parkingapp.viewmodels.UserViewModel;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edFirstName, edLastName, edEmail, edPassword, edConfPassword, edCarPlateNo, edContactNo;
    private Button btnSignUp;
    private UserViewModel userViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edFirstName = findViewById(R.id.ed_firstName);
        edLastName = findViewById(R.id.ed_lastName);
        edEmail = findViewById(R.id.ed_email);
        edPassword = findViewById(R.id.ed_password);
        edConfPassword = findViewById(R.id.ed_confPassword);
        edCarPlateNo = findViewById(R.id.ed_carPlateNo);
        edContactNo = findViewById(R.id.ed_contactNo);

        btnSignUp = findViewById(R.id.btn_signUp);
        btnSignUp.setOnClickListener(this);

        this.userViewModel = UserViewModel.getInstance();
    }

    @Override
    public void onClick(View view) {

        if (view != null){
            switch (view.getId()){
                case R.id.btn_signUp: {
                    if (this.validateData()){
                        //save data to database
                        this.saveUserToDB();
                        //go to main activity
                        this.goToMain();
                    }
                }
                default: break;
            }
        }
    }

    private void saveUserToDB(){
        User newUser = new User();
        newUser.setFirstName(edFirstName.getText().toString());
        newUser.setLastName(edLastName.getText().toString());
        newUser.setEmail(this.edEmail.getText().toString());
        newUser.setPassword(this.edPassword.getText().toString());
        newUser.setCarPlateNo(this.edCarPlateNo.getText().toString());
        newUser.setContactNo(this.edContactNo.getText().toString());
        newUser.setActive(true);

        //Save User to DB
        this.userViewModel.addUser(newUser);
        Toast.makeText(this, "User Registered Successfully.", Toast.LENGTH_SHORT).show();
    }


    private void goToMain() {
        this.finish();
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
    }

    private Boolean validateData(){

        if (this.edFirstName.getText().toString().isEmpty()){
            this.edFirstName.setError("Please enter firstname");
            return false;
        }

        if (this.edEmail.getText().toString().isEmpty()){
            this.edEmail.setError("Please enter an email");
            return false;
        }

       if (!ValidateData.isValidEmailId(this.edEmail.getText().toString())){
           this.edEmail.setError("InValid Email Address.");
           return false;
       }

        if (this.edPassword.getText().toString().isEmpty()){
            this.edPassword.setError("Password cannot be empty");
            return false;
        }

        if (this.edConfPassword.getText().toString().isEmpty()){
            this.edConfPassword.setError("Please provide confirm password");
            return false;
        }

        if (!edPassword.getText().toString().equals(this.edConfPassword.getText().toString())){
            this.edConfPassword.setError("Both passwords must be same");
            return false;
        }

        if (this.edCarPlateNo.getText().toString().isEmpty()){
            this.edCarPlateNo.setError("Please provide your car plate no.");
            return false;
        }

        if (this.edContactNo.getText().toString().isEmpty()){
            this.edContactNo.setError("Please provide your contact number.");
            return false;
        }

        if (!ValidateData.isValidPhoneNumber(edContactNo.getText().toString())){
            this.edContactNo.setError("Please provide valid contact number.");
            return false;
        }
        return true;
    }

}