package com.example.popularmovies;

import com.google.gson.annotations.SerializedName;

public class JsonModel {
    @SerializedName("page")
    Integer page;
    @SerializedName("total_results")
    Integer total_results;
    @SerializedName("total_pages")
    Integer total_pages;
    @SerializedName("results")
   public My_Result []results;

}
class My_Result {
    @SerializedName("vote_count")
    Integer vote_count;
    @SerializedName("video")
    Boolean video;
    @SerializedName("vote_average")
    String vote_average;
    @SerializedName("id")
    Integer id;
    @SerializedName("title")
    String title;
    @SerializedName("popularity")
    Double popularity;
    @SerializedName("poster_path")
    String poster_path;
    @SerializedName("original_language")
    String original_language;
    @SerializedName("overview")
    String overview;
    @SerializedName("release_date")
    String release_date;

}