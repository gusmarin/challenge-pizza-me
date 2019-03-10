package com.gmarin.challenge.pizzame.data.network.yelp;

import com.gmarin.challenge.pizzame.data.IDataCallback;
import com.gmarin.challenge.pizzame.data.IDataImpl;
import com.gmarin.challenge.pizzame.data.network.yelp.model.Businesses;
import com.gmarin.challenge.pizzame.data.network.yelp.model.BusinessDistanceComparator;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class YelpRepository implements IDataImpl {
    private static final String BASE_URL = "https://api.yelp.com";
    // TODO hide this key
    protected static final String API_KEY = "Bearer TFmaxhGD522LOAxMjLExlKsIfu5j5J5FsdR1UuwQwaZ1EzoGiPLpddlIrdlwVGbFDxz9MVVSpS32ItWDewpjCqT-5JHC92ym2hRjamDmCJ-N8mpWMIGIu0oeWvJ_XHYx";

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

    @Override
    public void getNearestPlaces(String latitude, String longitude, String term, final IDataCallback callback) {
        YelpWebService webService = mRetrofit.create(YelpWebService.class);
        Call<Businesses> call = webService.getNearestBusiness(latitude, longitude, term);

        call.enqueue(new Callback<Businesses>(){
            @Override
            public void onFailure(Call<Businesses> call, Throwable t) {
                callback.onFailure("Fail to obtain data");
            }

            @Override
            public void onResponse(Call<Businesses> call, Response<Businesses> response) {
                if (response.isSuccessful()) {
                    Businesses businesses = response.body();
                    businesses.getBusinesses().sort(new BusinessDistanceComparator());
                    callback.onSuccess(businesses);
                }
            }
        });
    }
}
