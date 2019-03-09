package com.gmarin.challenge.pizzame.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Business {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("image_url")
    private String image_url;

    @SerializedName("rating")
    private double rating;

    @SerializedName("display_phone")
    private String display_phone;

    @SerializedName("distance")
    private double distance;

    @SerializedName("location")
    private Location location;

    public static class Location {
        @SerializedName("display_address")
        private List<String> display_address;

        public List<String> getDisplay_address() {
            return display_address;
        }

        public void setDisplay_address(List<String> display_address) {
            this.display_address = display_address;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getDisplay_phone() {
        return display_phone;
    }

    public void setDisplay_phone(String display_phone) {
        this.display_phone = display_phone;
    }

    public double getDistance() {
        return distance/1000;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
