package com.gmarin.challenge.pizzame.data.network.yelp;

import com.gmarin.challenge.pizzame.data.model.Business;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YelpWebService {
    @GET("/v3/businesses/search")
    Call<List<Business>> getNearestBusiness(
        @Query("latitude") String latitude,
        @Query("longitude") String longitude,
        @Query("term") String term);
}
