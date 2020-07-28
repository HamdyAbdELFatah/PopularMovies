package com.example.popularmovies.model;

import com.google.gson.annotations.SerializedName;

public class JsonModel {
    public Integer getPage() {
        return page;
    }

    public Integer getTotal_results() {
        return total_results;
    }

    public Integer getTotal_pages() {
        return total_pages;
    }

    public My_Result[] getResults() {
        return results;
    }

    @SerializedName("page")
    Integer page;
    @SerializedName("total_results")
    Integer total_results;
    @SerializedName("total_pages")
    Integer total_pages;
    @SerializedName("results")
   public My_Result []results;

}
