package com.bcaf.bus.model.ticket;

import com.bcaf.bus.model.TripSchedule.TripSchedule;
import com.google.gson.annotations.SerializedName;

public class Ticket {
    @SerializedName("id")
    private Integer id;

    @SerializedName("seatNumber")
    private Integer seatNumber;

    @SerializedName("cancellable")
    private Boolean cancellable;

    @SerializedName("journeyDate")
    private String journeyDate;

    @SerializedName("tripSchedule")
    private TripSchedule tripSchedule;

    public Ticket() {
    }

    public Ticket(Integer id, Integer seatNumber, Boolean cancellable, String journeyDate, TripSchedule tripSchedule) {
        this.id = id;
        this.seatNumber = seatNumber;
        this.cancellable = cancellable;
        this.journeyDate = journeyDate;
        this.tripSchedule = tripSchedule;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Boolean getCancellable() {
        return cancellable;
    }

    public void setCancellable(Boolean cancellable) {
        this.cancellable = cancellable;
    }

    public String getJourneyDate() {
        return journeyDate;
    }

    public void setJourneyDate(String journeyDate) {
        this.journeyDate = journeyDate;
    }

    public TripSchedule getTripSchedule() {
        return tripSchedule;
    }

    public void setTripSchedule(TripSchedule tripSchedule) {
        this.tripSchedule = tripSchedule;
    }
}
