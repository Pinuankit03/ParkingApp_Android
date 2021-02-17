package com.example.parkingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parkingapp.R;
import com.example.parkingapp.common.OnParkingClickListener;
import com.example.parkingapp.model.Parking;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//Student ID - 101334143
//Student Name - Pinalben Patel

public class ParkingListAdapter extends RecyclerView.Adapter<ParkingListAdapter.ParkinglistViewHolder> {
    private final Context context;
    private final ArrayList<Parking> parkingsList;
    static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private OnParkingClickListener clickListener;

    public ParkingListAdapter(Context context, ArrayList<Parking> parkingsList, OnParkingClickListener clickListener) {
        this.context = context;
        this.parkingsList = parkingsList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ParkingListAdapter.ParkinglistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.parking_list_item, parent, false);
        return new ParkinglistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParkinglistViewHolder holder, int position) {
        holder.bind(parkingsList.get(position), clickListener);
    }

    @Override
    public int getItemCount() {
        return parkingsList.size();
    }

    public static class ParkinglistViewHolder extends RecyclerView.ViewHolder {
        TextView txtAddress, txtParkingDate, txtParkingHours, txtCarPlateNo;
        LinearLayout linearMain;

        public ParkinglistViewHolder(@NonNull View itemView) {
            super(itemView);
            txtAddress = itemView.findViewById(R.id.tvParkingAddress);
            txtParkingDate = itemView.findViewById(R.id.tvParkingDate);
            txtParkingHours = itemView.findViewById(R.id.tvParkingHours);
            txtCarPlateNo = itemView.findViewById(R.id.tvCarPlateNo);
            linearMain = itemView.findViewById(R.id.linearMain);
        }

        public void bind(Parking parking, OnParkingClickListener clickListener) {


            Date d = parking.getParkingDate();
            String strDate = dateFormat.format(d);
            txtAddress.setText(parking.getStreetAddress());
            txtParkingHours.setText(parking.getHoursToPark());
            txtCarPlateNo.setText("Car Plate No : " + parking.getCarPlateNo());
            txtParkingDate.setText(strDate);
            linearMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onParkingClickListener(parking);

                }
            });

        }
    }
}

