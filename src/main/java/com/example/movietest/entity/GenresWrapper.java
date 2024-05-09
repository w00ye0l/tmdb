package com.example.movietest.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public class GenresWrapper {

    @JsonProperty("genres")
    private List<Genre> genres;

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }
}
