package com.example.parkingapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

import java.util.ArrayList;
import java.util.List;

//Student ID - 101334143
//Student Name - Pinalben Patel

public class HomeFragment extends Fragment implements OnParkingClickListener {
    private final String TAG = this.getClass().getCanonicalName();
    private ParkingListAdapter adapter;
    private ArrayList<Parking> parkingArrayList;
    private ParkingViewModel parkingViewModel;
    private String userID;
    private RecyclerView rvParking;
    private LinearLayoutManager viewManager;
    private PreferenceSettings mPreferenceSettings;
    private TextView txtNoData;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        this.parkingViewModel = ParkingViewModel.getInstance();
        this.mPreferenceSettings = new PreferenceSettings(getActivity());
        this.parkingArrayList = new ArrayList<>();

        this.userID = mPreferenceSettings.getUserID();
        this.rvParking = root.findViewById(R.id.recycleView);
        this.txtNoData = root.findViewById(R.id.tvNoData);

        this.adapter = new ParkingListAdapter(getActivity().getApplicationContext(), parkingArrayList, this);
        this.viewManager = new LinearLayoutManager(getActivity().getApplicationContext());
        this.rvParking.setAdapter(this.adapter);
        this.rvParking.setLayoutManager(this.viewManager);
        this.rvParking.setHasFixedSize(true);
        this.rvParking.addItemDecoration(new DividerItemDecoration(getActivity().getApplicationContext(), 0));


        this.parkingViewModel.getAllParking(this.userID);

        //getting updated parking list which is user already booked.
        this.parkingViewModel.getParkingRepository().parkingItems.observe(getActivity(), new Observer<List<Parking>>() {
            @Override
            public void onChanged(List<Parking> parkingsList) {
                if (parkingsList != null) {
                    // Log.e(TAG, "Data Changed : " + parkingsList.toString());
                    parkingArrayList.clear();
                    parkingArrayList.addAll(parkingsList);
                    if (parkingArrayList.size() > 0 && parkingArrayList != null) {
                        txtNoData.setVisibility(View.GONE);
                        rvParking.setVisibility(View.VISIBLE);
                        adapter.notifyDataSetChanged();
                    } else {
                        txtNoData.setVisibility(View.VISIBLE);
                        rvParking.setVisibility(View.GONE);
                    }
                }
            }
        });

        return root;
    }

    @Override
    public void onParkingClickListener(Parking parking) {
//        Log.e("Details", "parking detail : " + parking.toString());
//        Log.e("ID", "parking id : " + parking.getId());
        Intent intent = new Intent(getActivity(), ParkingDetailActivity.class);
        intent.putExtra("id", parking.getId());
        intent.putExtra("userID", this.userID);
        startActivity(intent);
    }
}