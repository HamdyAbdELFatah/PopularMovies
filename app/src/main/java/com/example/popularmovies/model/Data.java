package com.example.popularmovies.model;

public class Data {
    String vote_average;

    public Data(String vote_average, String title, String poster_path, String original_language, String overview, String release_date) {
        this.vote_average = vote_average;
        this.title = title;
        this.poster_path = poster_path;
        this.original_language = original_language;
        this.overview = overview;
        this.release_date = release_date;
    }

    public String getVote_average() {
        return vote_average;
    }

    public String getTitle() {
        return title;
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

    String title;
    String poster_path;
    String original_language;
    String overview;
    String release_date;
}
