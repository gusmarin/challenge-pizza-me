package com.gmarin.challenge.pizzame.data.entity;


public class Place {

    private final String name;
    private final String imageUrl;
    private final float rating;
    private final int reviewCount;
    private final String phoneNumber;
    private final double distance;
    private final String address;

    protected Place (String name, String imageUrl, float rating, int reviewCount, String phoneNumber,
                   double distance, String address) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.reviewCount = reviewCount;
        this.phoneNumber = phoneNumber;
        this.distance = distance;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public float getRating() {
        return rating;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public double getDistance() {
        return distance;
    }

    public String getAddress() {
        return address;
    }

    public static class PlaceBuilder {
        private String name = "Place";
        private String imageUrl;
        private float rating = 0;
        private int reviewCount = 0;
        private String phoneNumber = "(000) 000-0000";
        private double distance = 0;
        private String address = "Somewhere";

        public PlaceBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public PlaceBuilder setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public PlaceBuilder setRating(float rating) {
            this.rating = rating;
            return this;
        }

        public PlaceBuilder setReviewCount(int reviewCount) {
            this.reviewCount = reviewCount;
            return this;
        }

        public PlaceBuilder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public PlaceBuilder setDistance(double distance) {
            this.distance = distance;
            return this;
        }

        public PlaceBuilder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Place build() {
            return new Place(name, imageUrl, rating, reviewCount, phoneNumber, distance, address);
        }
    }
}
