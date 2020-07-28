package com.example.popularmovies;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static String BASE_URL="https://api.themoviedb.org/3/discover/";
    private static Retrofit instance;
    public static Connection getInstance(){
        if(instance==null) {
                 instance = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
        }
        return instance.create(Connection.class);
    }
}
