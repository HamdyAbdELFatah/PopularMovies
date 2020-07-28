package com.example.popularmovies.model;

import com.google.gson.annotations.SerializedName;

public class My_Result {
    public Integer getVote_count() {
        return vote_count;
    }

    public Boolean getVideo() {
        return video;
    }

    public String getVote_average() {
        return vote_average;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Double getPopularity() {
        return popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

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
