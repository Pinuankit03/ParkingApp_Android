package com.example.parkingapp.viewmodels;

import androidx.lifecycle.ViewModel;

import com.example.parkingapp.model.User;
import com.example.parkingapp.repositories.UserRepository;

//Student ID - 101334143
//Student Name - Pinalben Patel

public class UserViewModel extends ViewModel {
    private static final UserViewModel ourInstance = new UserViewModel();
    private final UserRepository userRepository = new UserRepository();

    public static UserViewModel getInstance() {
        return ourInstance;
    }

    private UserViewModel() {
    }

    public void addUser(User user){
        this.userRepository.addUser(user);
    }

    public UserRepository getUserRepository(){
        return userRepository;
    }

    public void validateUser(String email, String password) {
        if (!email.isEmpty()) {
            if (!password.isEmpty()) {
                this.userRepository.getUser(email, password);
            }
        }
    }

    public void getUserByID(String userID) {
        this.userRepository.getUserById(userID);
    }

    public void updateProfile(String userID, User user) {
        this.userRepository.updateProfile(userID, user);
    }

    public void updateProfileStatus(String userID, boolean isActive) {
        this.userRepository.updateProfileStatus(userID, isActive);
    }
}
