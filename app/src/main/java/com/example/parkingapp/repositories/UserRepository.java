package com.example.parkingapp.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.parkingapp.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

//Student ID - 101334143
//Student Name - Pinalben Patel

public class UserRepository {
    private final String TAG = this.getClass().getCanonicalName();
    private final String COLLECTION_NAME_USER = "User";
    private final FirebaseFirestore db;
    public MutableLiveData<String> signInStatus = new MutableLiveData<String>();
    public MutableLiveData<String> loggedInUserID = new MutableLiveData<String>();
    public MutableLiveData<User> userData = new MutableLiveData<>();

    public UserRepository() {
        db = FirebaseFirestore.getInstance();
    }

    public void addUser(User user){
        try {
            db.collection(COLLECTION_NAME_USER)
                    .add(user)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "Document added with ID : " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "Error adding document to the store " + e);
                        }
                    });

        }catch (Exception ex){
            Log.e(TAG, ex.toString());
            Log.e(TAG, ex.getLocalizedMessage());
        }
    }

    public void getUser(String email, String password){
        this.signInStatus.postValue("LOADING");

        try{
            db.collection(COLLECTION_NAME_USER)
                    .whereEqualTo("email", email)
//                    .whereEqualTo("password", password)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()){
                                if (task.getResult().getDocuments().size() != 0){
                                    if (task.getResult().getDocuments().get(0).toObject(User.class).getPassword().equals(password) &&
                                        task.getResult().getDocuments().get(0).toObject(User.class).isActive() == true){
                                        signInStatus.postValue("SUCCESS");

                                        //get the Id of currently logged in user
                                        loggedInUserID.postValue(task.getResult().getDocuments().get(0).getId());
                                        Log.d(TAG, "Logged in user document ID : " + loggedInUserID);
                                    }else{
                                        signInStatus.postValue("FAILURE");
                                    }
                                }else{
                                    signInStatus.postValue("FAILURE");
                                }
                            }else{
                                Log.e(TAG, "Error fetching document" + task.getException());
                                signInStatus.postValue("FAILURE");
                            }
                        }
                    });
        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
            Log.e(TAG, ex.getLocalizedMessage());
            signInStatus.postValue("FAILURE");
        }
    }

    public void getUserById(String userID) {

        try {
            db.collection(COLLECTION_NAME_USER)
                    .document(userID)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot != null) {
                                userData.postValue(documentSnapshot.toObject(User.class));
                                //Log.d(TAG, userData.getValue().getFirstName());
                            }
                        }
                    });

        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
            Log.e(TAG, ex.getLocalizedMessage());
        }
    }

    public void updateProfile(String userID, User user) {
        try {
            db.collection(COLLECTION_NAME_USER)
                    .document(userID)
                    .set(user)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.e(TAG, "Document updated successfully");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "Failure updating document");
                        }
                    });
        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
            Log.e(TAG, ex.getLocalizedMessage());
        }
    }

    public void updateProfileStatus(String userID, Boolean isActive) {
        try {
            db.collection(COLLECTION_NAME_USER)
                    .document(userID)
                    .update("active", isActive)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.e(TAG, "User Profile Deleted successfully");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "Failure while deleting profile.");
                        }
                    });
        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
            Log.e(TAG, ex.getLocalizedMessage());
        }
    }
}
