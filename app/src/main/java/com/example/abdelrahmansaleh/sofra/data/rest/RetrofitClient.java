package com.example.abdelrahmansaleh.sofra.data.rest;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static final String BaseUrl = "http://ipda3.com/sofra/api/v1/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl( BaseUrl )
                    .addConverterFactory( GsonConverterFactory.create() )
                    .build();
        }
        return retrofit;
    }
}
