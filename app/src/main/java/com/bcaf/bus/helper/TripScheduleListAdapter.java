package com.bcaf.bus.helper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bcaf.bus.R;
import com.bcaf.bus.controller.BookingActivity;
import com.bcaf.bus.model.TripSchedule.TripSchedule;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TripScheduleListAdapter extends  RecyclerView.Adapter<TripScheduleListAdapter.MyViewHolder> {
    List<TripSchedule> tripScheduleList;
    Context context;

    public TripScheduleListAdapter(List<TripSchedule> tripScheduleList, Context context) {
        this.tripScheduleList = tripScheduleList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_trip_schedule_item, parent, false);
        return new TripScheduleListAdapter.MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TripScheduleListAdapter.MyViewHolder holder, int position) {
        holder.txtJourneyDate.setText(tripScheduleList.get(position).getTripDate());
        holder.txtAvailableSeats.setText(tripScheduleList.get(position).getAvailableSeats().toString());
        holder.txtSourceStop.setText(tripScheduleList.get(position).getTripDetail().getSourceStop().getName());
        holder.txtDestinationStop.setText(tripScheduleList.get(position).getTripDetail().getDestStop().getName());
        holder.txtAgency.setText(tripScheduleList.get(position).getTripDetail().getAgency().getName());
        holder.txtBus.setText(tripScheduleList.get(position).getTripDetail().getBus().getCode());

        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("id", tripScheduleList.get(position).getId().toString());
            Intent i = new Intent(v.getContext(), BookingActivity.class);
            i.putExtras(bundle);
            v.getContext().startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return tripScheduleList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView txtJourneyDate, txtAvailableSeats, txtSourceStop, txtDestinationStop, txtAgency, txtBus;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtJourneyDate = itemView.findViewById(R.id.txtJourneyDate);
            txtAvailableSeats = itemView.findViewById(R.id.txtAvailableSeats);
            txtSourceStop = itemView.findViewById(R.id.txtSourceStop);
            txtDestinationStop = itemView.findViewById(R.id.txtDestinationStop);
            txtAgency = itemView.findViewById(R.id.txtAgency);
            txtBus = itemView.findViewById(R.id.txtBus);
        }
    }
}