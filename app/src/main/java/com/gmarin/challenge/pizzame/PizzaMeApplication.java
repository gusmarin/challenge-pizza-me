package com.gmarin.challenge.pizzame;

import android.app.Application;

import com.gmarin.challenge.pizzame.data.network.yelp.model.Business;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PizzaMeApplication extends Application {
    public static Map<String, Business> mBusinessCache = new HashMap<String, Business>();

    public static void setBusinessCache(List<Business> list) {
        for (Business business : list) {
            mBusinessCache.put(business.getId(), business);
        }
    }

    public static Business getBusiness(String key) {
        return mBusinessCache.getOrDefault(key, null);
    }
}
