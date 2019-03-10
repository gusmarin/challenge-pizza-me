package com.gmarin.challenge.pizzame.data.network.yelp;

import com.gmarin.challenge.pizzame.data.network.yelp.model.Businesses;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface YelpWebService {
    @Headers("Authorization: " + YelpRepository.API_KEY)
    @GET("/v3/businesses/search")
    Call<Businesses> getNearestBusiness(
        @Query("latitude") String latitude,
        @Query("longitude") String longitude,
        @Query("term") String term);
}
