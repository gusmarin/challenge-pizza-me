package com.gmarin.challenge.pizzame.data.network;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class YelpRepository {
    private static final String BASE_URL = "https://api.yelp.com";
    // TODO hide this key
    private static final String API_KEY = "TFmaxhGD522LOAxMjLExlKsIfu5j5J5FsdR1UuwQwaZ1EzoGiPLpddlIrdlwVGbFDxz9MVVSpS32ItWDewpjCqT-5JHC92ym2hRjamDmCJ-N8mpWMIGIu0oeWvJ_XHYx";

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

}
