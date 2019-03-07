package com.gmarin.challenge.pizzame.data.network.yelp;

import androidx.lifecycle.MutableLiveData;

import com.gmarin.challenge.pizzame.data.model.Business;
import com.gmarin.challenge.pizzame.data.model.BusinessDistanceComparator;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class YelpRepository {
    private static final String BASE_URL = "https://api.yelp.com";
    // TODO hide this key
    private static final String API_KEY = "TFmaxhGD522LOAxMjLExlKsIfu5j5J5FsdR1UuwQwaZ1EzoGiPLpddlIrdlwVGbFDxz9MVVSpS32ItWDewpjCqT-5JHC92ym2hRjamDmCJ-N8mpWMIGIu0oeWvJ_XHYx";
    private static final String PIZZA_TERM = "pizza";

    private Retrofit mRetrofit;
    private OkHttpClient mClient;

    public YelpRepository() {
        //TODO consider moving this to a later stage to handle error
        boolean isOnline;
        try {
            mClient = getClient();
            isOnline = true;
        } catch (IOException e) {
            // fail silently
            isOnline = false;
        }

        if (isOnline) {
            mRetrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(mClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }

    public OkHttpClient getClient() throws IOException {
        return new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + API_KEY)
                        .build();
                return chain.proceed(newRequest);
            }
        }).build();
    }

    public MutableLiveData<List<Business>> searchNearestBusinesses(String latitude, String longitude) {

        final MutableLiveData<List<Business>> data = new MutableLiveData<>();
        YelpWebService webService = mRetrofit.create(YelpWebService.class);
        Call<List<Business>> call = webService.getNearestBusiness(latitude, longitude, PIZZA_TERM);
        call.enqueue(new Callback<List<Business>>(){

            @Override
            public void onFailure(Call<List<Business>> call, Throwable t) {
                data.setValue(null);
            }

            @Override
            public void onResponse(Call<List<Business>> call, Response<List<Business>> response) {
                if (response.isSuccessful()) {
                    List<Business> businesses = response.body();
                    Collections.sort(businesses, new BusinessDistanceComparator());
                    data.setValue(businesses);
                }
            }
        });


        return data;
    }

}
