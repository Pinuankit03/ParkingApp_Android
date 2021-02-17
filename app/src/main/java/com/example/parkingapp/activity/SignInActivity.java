package com.example.parkingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.parkingapp.R;
import com.example.parkingapp.common.PreferenceSettings;
import com.example.parkingapp.viewmodels.UserViewModel;

//Student ID - 101334143
//Student Name - Pinalben Patel

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edEmail, edPassword;
    private Button btnLogin;
    private ProgressBar progressBar;
    private UserViewModel userViewModel;
    private TextView txtCreateAcc;
    private CheckBox mRememberPassword;
    private boolean rememberMe;
    private PreferenceSettings mPreferenceSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        this.mPreferenceSettings = new PreferenceSettings(this);

        if (this.mPreferenceSettings.getIsLogin()) {
            Intent mainIntent = new Intent(this, MainActivity.class);
            startActivity(mainIntent);
            finish();
        }

        this.userViewModel = UserViewModel.getInstance();
        edEmail = findViewById(R.id.ed_email);
        edPassword = findViewById(R.id.ed_password);
        btnLogin = findViewById(R.id.btn_login);
        txtCreateAcc = findViewById(R.id.tvCreateAcc);
        mRememberPassword = (CheckBox) findViewById(R.id.remember_password);
        progressBar = findViewById(R.id.progressBar);

        btnLogin.setOnClickListener(this);
        txtCreateAcc.setOnClickListener(this);


        this.userViewModel.getUserRepository().signInStatus.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String status) {
                if (status.equals("SUCCESS")) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Login Successful.", Toast.LENGTH_SHORT).show();
                    goToMain();
                } else if (status.equals("LOADING")) {
                    progressBar.setVisibility(View.VISIBLE);
                } else if (status.equals("FAILURE")) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Please provide valid username and password.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.tvCreateAcc: {
                    Intent signUpIntent = new Intent(this, SignUpActivity.class);
                    startActivity(signUpIntent);
                    break;
                }
                case R.id.btn_login: {
                    //verify the user
                    this.rememberMe = mRememberPassword.isChecked();
                    this.validateLogin();
                    break;
                }
                default:
                    break;
            }
        }
    }

    private void validateLogin() {
        String email = this.edEmail.getText().toString();
        String password = this.edPassword.getText().toString();
        this.userViewModel.validateUser(email, password);
    }


    private void goToMain() {
        if (rememberMe) {
            mPreferenceSettings.setIslogin(true);
        }
        this.finish();
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
    }
}