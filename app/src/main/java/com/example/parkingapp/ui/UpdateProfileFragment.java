package com.example.parkingapp.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.parkingapp.R;
import com.example.parkingapp.common.ValidateData;
import com.example.parkingapp.model.User;
import com.example.parkingapp.viewmodels.UserViewModel;

public class UpdateProfileFragment extends Fragment implements View.OnClickListener {


    private UserViewModel userViewModel;
    private String userID;
    private EditText edtFirstName, edtLastname, edtEmail, edtContactNo, edtCarPlateNo;
    private Button btnUpdateProfile;
    private User userData;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_update_profile, container, false);
        this.edtFirstName = root.findViewById(R.id.edFirstName);
        this.edtLastname = root.findViewById(R.id.edLastName);
        this.edtEmail = root.findViewById(R.id.edEmail);
        this.edtContactNo = root.findViewById(R.id.ed_contactNo);
        this.edtCarPlateNo = root.findViewById(R.id.edCarPlateno);
        this.btnUpdateProfile = root.findViewById(R.id.btn_update_profile);
        this.btnUpdateProfile.setOnClickListener(this);

        this.userViewModel = UserViewModel.getInstance();
        this.userID = this.userViewModel.getUserRepository().loggedInUserID.getValue();
        Log.e("userID update profile", userID);

        this.userViewModel.getUserByID(userID);
        this.userViewModel.getUserRepository().userData.observe(getActivity(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                userData = user;
                Log.e("User login ", user.getFirstName());
                edtFirstName.setText(user.getFirstName());
                edtLastname.setText(user.getLastName());
                edtContactNo.setText(user.getContactNo());
                edtEmail.setText(user.getEmail());
                edtCarPlateNo.setText(user.getCarPlateNo());
            }
        });
        return root;
    }

    @Override
    public void onClick(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.btn_update_profile: {
                    if (validateData()) {
                        User updatedUser = new User();
                        updatedUser.setFirstName(edtFirstName.getText().toString());
                        updatedUser.setLastName(edtLastname.getText().toString());
                        updatedUser.setEmail(this.edtEmail.getText().toString());
                        updatedUser.setCarPlateNo(this.edtCarPlateNo.getText().toString());
                        updatedUser.setContactNo(this.edtContactNo.getText().toString());
                        updatedUser.setPassword(userData.getPassword());
                        updatedUser.setActive(true);
                        this.userViewModel.updateProfile(userID, updatedUser);
                        Toast.makeText(getActivity(), "Updated Successfully.", Toast.LENGTH_SHORT).show();
                    }
                }
                default:
                    break;
            }
        }
    }

    private Boolean validateData() {

        if (this.edtFirstName.getText().toString().isEmpty()) {
            this.edtFirstName.setError("Please enter firstname");
            return false;
        }

        if (this.edtEmail.getText().toString().isEmpty()) {
            this.edtEmail.setError("Please enter an email");
            return false;
        }

        if (!ValidateData.isValidEmailId(this.edtEmail.getText().toString())) {
            this.edtEmail.setError("InValid Email Address.");
            return false;
        }

        if (this.edtCarPlateNo.getText().toString().isEmpty()) {
            this.edtCarPlateNo.setError("Please provide your car plate no.");
            return false;
        }

        if (this.edtContactNo.getText().toString().isEmpty()) {
            this.edtContactNo.setError("Please provide your contact number.");
            return false;
        }

        if (!ValidateData.isValidPhoneNumber(edtContactNo.getText().toString())) {
            this.edtContactNo.setError("Please provide valid contact number.");
            return false;
        }
        return true;
    }
}