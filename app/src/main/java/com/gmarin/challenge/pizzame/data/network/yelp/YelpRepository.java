package com.gmarin.challenge.pizzame.data.network.yelp;

import androidx.lifecycle.MutableLiveData;

import com.gmarin.challenge.pizzame.data.model.Business;
import com.gmarin.challenge.pizzame.data.model.Businesses;
import com.gmarin.challenge.pizzame.data.model.BusinessDistanceComparator;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class YelpRepository {
    private static final String BASE_URL = "https://api.yelp.com";
    // TODO hide this key
    protected static final String API_KEY = "Bearer TFmaxhGD522LOAxMjLExlKsIfu5j5J5FsdR1UuwQwaZ1EzoGiPLpddlIrdlwVGbFDxz9MVVSpS32ItWDewpjCqT-5JHC92ym2hRjamDmCJ-N8mpWMIGIu0oeWvJ_XHYx";
    private static final String PIZZA_TERM = "pizza";

    private Retrofit mRetrofit;

    public YelpRepository() {
        mRetrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public OkHttpClient getClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return new OkHttpClient.Builder().addInterceptor(logging).build();
    }

    public MutableLiveData<List<Business>> searchNearestBusinesses(String latitude, String longitude) {

        final MutableLiveData<List<Business>> data = new MutableLiveData<>();
        YelpWebService webService = mRetrofit.create(YelpWebService.class);
        Call<Businesses> call = webService.getNearestBusiness(latitude, longitude, PIZZA_TERM);
        call.enqueue(new Callback<Businesses>(){

            @Override
            public void onFailure(Call<Businesses> call, Throwable t) {
                data.setValue(null);
            }

            @Override
            public void onResponse(Call<Businesses> call, Response<Businesses> response) {
                if (response.isSuccessful()) {
                    Businesses businesses = response.body();
                    businesses.getBusinesses().sort(new BusinessDistanceComparator());
                    data.setValue(businesses.getBusinesses());
                }
            }
        });


        return data;
    }

}
