package com.gmarin.challenge.pizzame.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Businesses {

    @SerializedName("businesses")
    private List<Business> businesses;

    public List<Business> getBusinesses() {
        return businesses;
    }

    public void setBusinesses(List<Business> businesses) {
        this.businesses = businesses;
    }
}

