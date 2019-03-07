package com.gmarin.challenge.pizzame.data.model;

import java.util.Comparator;

public class BusinessDistanceComparator implements Comparator<Business> {
    @Override
    public int compare(Business a, Business b) {
        return Double.compare(a.getDistance(), b.getDistance());
    }
}
