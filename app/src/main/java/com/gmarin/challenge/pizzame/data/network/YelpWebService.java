package com.gmarin.challenge.pizzame.data.network;

import com.gmarin.challenge.pizzame.data.model.Business;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YelpWebService {
    @GET("/v3/businesses/search")
    Call<Business> getNearestBusiness(
        @Query("latitude") String latitude,
        @Query("longitude") String longitude,
        @Query("term") String term);
}
