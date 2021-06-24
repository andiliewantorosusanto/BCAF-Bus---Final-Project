package com.bcaf.bus.model.trip;

import com.bcaf.bus.model.agency.Agency;
import com.bcaf.bus.model.bus.Bus;
import com.bcaf.bus.model.stop.Stop;
import com.google.gson.annotations.SerializedName;

public class Trip {
    @SerializedName("id")
    private Integer id;

    @SerializedName("fare")
    private Integer fare;

    @SerializedName("journeyTime")
    private String journeyTime;

    @SerializedName("sourceStop")
    private Stop sourceStop;

    @SerializedName("destStop")
    private Stop destStop;

    @SerializedName("bus")
    private Bus bus;

    @SerializedName("agency")
    private Agency agency;

    public Trip() {
    }

    public Trip(Integer id, Integer fare, String journeyTime, Stop sourceStop, Stop destStop, Bus bus, Agency agency) {
        this.id = id;
        this.fare = fare;
        this.journeyTime = journeyTime;
        this.sourceStop = sourceStop;
        this.destStop = destStop;
        this.bus = bus;
        this.agency = agency;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFare() {
        return fare;
    }

    public void setFare(Integer fare) {
        this.fare = fare;
    }

    public String getJourneyTime() {
        return journeyTime;
    }

    public void setJourneyTime(String journeyTime) {
        this.journeyTime = journeyTime;
    }

    public Stop getSourceStop() {
        return sourceStop;
    }

    public void setSourceStop(Stop sourceStop) {
        this.sourceStop = sourceStop;
    }

    public Stop getDestStop() {
        return destStop;
    }

    public void setDestStop(Stop destStop) {
        this.destStop = destStop;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public Agency getAgency() {
        return agency;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }
}
