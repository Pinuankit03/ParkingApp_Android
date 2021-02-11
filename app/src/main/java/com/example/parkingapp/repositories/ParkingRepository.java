package com.example.parkingapp.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.example.parkingapp.model.Parking;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ParkingRepository {
    private final String TAG = this.getClass().getCanonicalName();
    private FirebaseFirestore db;
    private final String COLLECTION_NAME_PARKING = "Parking";
    private final String COLLECTION_NAME_USER = "User";

    public MutableLiveData<List<Parking>> parkingItems = new MutableLiveData<List<Parking>>();

    public ParkingRepository(){
        this.db = FirebaseFirestore.getInstance();
    }

    public void addParkingItem(String userID, Parking parking){
        try{
            db.collection(COLLECTION_NAME_USER)
                    .document(userID)
                    .collection(COLLECTION_NAME_PARKING)
                    .add(parking)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "Checklist item added with ID : "+ documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "Error adding checklist document");
                        }
                    });

        }catch (Exception ex){
            Log.e(TAG, ex.toString());
            Log.e(TAG, ex.getLocalizedMessage());
        }
    }

    public void getAllParking(String userID){
        try{
            db.collection(COLLECTION_NAME_USER)
                    .document(userID)
                    .collection(COLLECTION_NAME_PARKING)
                   // .orderBy("dateCreated", Query.Direction.DESCENDING)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                            if (error != null){
                                Log.e(TAG, "Listening to checklist changes FAILED", error);
                                return;
                            }

                            List<Parking> tempChecklistItems = new ArrayList<>();

                            if (snapshot != null){
                                Log.d(TAG, "Current data : " + snapshot.getDocumentChanges());

                                for (DocumentChange documentChange : snapshot.getDocumentChanges()){

                                    Parking parkinglist = documentChange.getDocument().toObject(Parking.class);
                                    parkinglist.setId(documentChange.getDocument().getId());

                                    switch (documentChange.getType()){
                                        case ADDED:
                                            tempChecklistItems.add(parkinglist);
                                            break;
                                        case MODIFIED:
                                            break;
                                        case REMOVED:
                                            break;
                                    }
                                }

                                Log.e(TAG, tempChecklistItems.toString());
                                parkingItems.postValue(tempChecklistItems);

                            }else{
                                Log.e(TAG, "No changes in checklist");
                            }
                        }
                    });

        }catch(Exception ex){
            Log.e(TAG, ex.getLocalizedMessage());
            Log.e(TAG, ex.toString());
        }
    }

}
