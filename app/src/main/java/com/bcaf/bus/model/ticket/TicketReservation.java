package com.bcaf.bus.model.ticket;

import com.google.gson.annotations.SerializedName;

public class TicketReservation {
    @SerializedName("seatNumber")
    private Integer seatNumber;

    @SerializedName("cancellable")
    private Boolean cancellable;

    @SerializedName("journeyDate")
    private String journeyDate;

    @SerializedName("tripScheduleId")
    private Integer tripScheduleId;

    @SerializedName("passengerId")
    private Integer passengerId;

    public TicketReservation() {
    }

    public TicketReservation(Integer seatNumber, Boolean cancellable, String journeyDate, Integer tripScheduleId, Integer passengerId) {
        this.seatNumber = seatNumber;
        this.cancellable = cancellable;
        this.journeyDate = journeyDate;
        this.tripScheduleId = tripScheduleId;
        this.passengerId = passengerId;
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

    public Integer getTripScheduleId() {
        return tripScheduleId;
    }

    public void setTripScheduleId(Integer tripScheduleId) {
        this.tripScheduleId = tripScheduleId;
    }

    public Integer getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(Integer passengerId) {
        this.passengerId = passengerId;
    }
}
