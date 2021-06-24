package com.bcaf.bus.helper;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bcaf.bus.R;
import com.bcaf.bus.model.ticket.Ticket;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TicketListAdapter extends  RecyclerView.Adapter<TicketListAdapter.MyViewHolder> {
    List<Ticket> ticketList;
    Context context;

    public TicketListAdapter(List<Ticket> ticketList, Context context) {
        this.ticketList = ticketList;
        this.context = context;
    }

    @Override
    public TicketListAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_ticket_item, parent, false);
        return new TicketListAdapter.MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TicketListAdapter.MyViewHolder holder, int position) {
        holder.txtJourneyDate.setText(ticketList.get(position).getTripSchedule().getTripDate());
        holder.txtAvailableSeats.setText(ticketList.get(position).getSeatNumber().toString());
        holder.txtSourceStop.setText(ticketList.get(position).getTripSchedule().getTripDetail().getSourceStop().getName());
        holder.txtDestinationStop.setText(ticketList.get(position).getTripSchedule().getTripDetail().getDestStop().getName());
        holder.txtAgency.setText(ticketList.get(position).getTripSchedule().getTripDetail().getAgency().getName());
        holder.txtBus.setText(ticketList.get(position).getTripSchedule().getTripDetail().getBus().getCode());

    }

    @Override
    public int getItemCount() {
        return ticketList.size();
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