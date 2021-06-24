package com.bcaf.bus.model.TripSchedule;

import com.bcaf.bus.model.trip.Trip;
import com.google.gson.annotations.SerializedName;

public class TripSchedule {
    @SerializedName("id")
    private Integer id;

    @SerializedName("tripDate")
    private String tripDate;

    @SerializedName("availableSeats")
    private Integer availableSeats;

    @SerializedName("tripDetail")
    private Trip tripDetail;

    public TripSchedule() {

    }

    public TripSchedule(Integer id, String tripDate, Integer availableSeats, Trip tripDetail) {
        this.id = id;
        this.tripDate = tripDate;
        this.availableSeats = availableSeats;
        this.tripDetail = tripDetail;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTripDate() {
        return tripDate;
    }

    public void setTripDate(String tripDate) {
        this.tripDate = tripDate;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

    public Trip getTripDetail() {
        return tripDetail;
    }

    public void setTripDetail(Trip tripDetail) {
        this.tripDetail = tripDetail;
    }
}
