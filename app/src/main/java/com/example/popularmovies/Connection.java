package com.example.popularmovies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Connection {
    @GET("movie?")
    Call<JsonModel> getData(@Query("api_key") String key,@Query("page")Integer page,@Query("sort_by")String Sort_by);

}
