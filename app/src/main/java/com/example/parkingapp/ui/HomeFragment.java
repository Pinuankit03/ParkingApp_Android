package com.example.parkingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parkingapp.R;
import com.example.parkingapp.activity.ParkingDetailActivity;
import com.example.parkingapp.adapter.ParkingListAdapter;
import com.example.parkingapp.common.OnParkingClickListener;
import com.example.parkingapp.common.PreferenceSettings;
import com.example.parkingapp.model.Parking;
import com.example.parkingapp.viewmodels.ParkingViewModel;
import com.example.parkingapp.viewmodels.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements OnParkingClickListener {
    private final String TAG = this.getClass().getCanonicalName();
    private ParkingListAdapter adapter;
    private ArrayList<Parking> parkingArrayList;
    private ParkingViewModel parkingViewModel;
    private UserViewModel userViewModel;
    private String userID;
    private RecyclerView rvParking;
    private LinearLayoutManager viewManager;
    private PreferenceSettings mPreferenceSettings;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        this.parkingViewModel = ParkingViewModel.getInstance();
        mPreferenceSettings = new PreferenceSettings(getActivity());

        this.userID = mPreferenceSettings.getUserID();
        this.rvParking = root.findViewById(R.id.recycleView);
        this.parkingArrayList = new ArrayList<>();
        this.adapter = new ParkingListAdapter(getActivity().getApplicationContext(), parkingArrayList, this);
        this.viewManager = new LinearLayoutManager(getActivity().getApplicationContext());


        this.rvParking.setAdapter(this.adapter);
        this.rvParking.setLayoutManager(this.viewManager);
        this.rvParking.setHasFixedSize(true);
        this.rvParking.addItemDecoration(new DividerItemDecoration(getActivity().getApplicationContext(), 0));

        this.parkingViewModel.getAllParking(this.userID);
        this.parkingViewModel.getParkingRepository().parkingItems.observe(getActivity(), new Observer<List<Parking>>() {
            @Override
            public void onChanged(List<Parking> checklists) {
                if (checklists != null) {
                    Log.e(TAG, "Data Changed : " + checklists.toString());
                    parkingArrayList.clear();
                    parkingArrayList.addAll(checklists);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        return root;
    }

    @Override
    public void onParkingClickListener(Parking parking) {
        Log.e("Details", "parking detail : " + parking.toString());
        Log.e("ID", "parking id : " + parking.getId());
        Intent intent = new Intent(getActivity(), ParkingDetailActivity.class);
        intent.putExtra("id", parking.getId());
        intent.putExtra("userID", this.userID);
        startActivity(intent);
    }
}