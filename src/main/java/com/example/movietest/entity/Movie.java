package com.example.movietest.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {

    @Id
    private Long id;
    private String genre_ids; // genre_ids를 대체하는 List<Genre>
    private String original_title;
    private String title;
    private String release_date;
    private String poster_path;
    @Column(length = 1000)
    private String overview;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // genre_ids를 가져올 때 String에서 List<Long>으로 변환
    public List<Long> getGenre_ids() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(this.genre_ids, new TypeReference<List<Long>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    // genre_ids를 저장할 때 List<Long>을 String으로 변환
    public void setGenre_ids(List<Long> genre_ids) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.genre_ids = mapper.writeValueAsString(genre_ids);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}
