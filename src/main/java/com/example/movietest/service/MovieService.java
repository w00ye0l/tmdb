package com.example.movietest.service;

import com.example.movietest.dto.MovieSearchDTO;
import com.example.movietest.entity.GenresWrapper;
import com.example.movietest.entity.MovieWrapper;

import java.util.List;

public interface MovieService {

    GenresWrapper getGenre();

    MovieWrapper getPopularMovie();

    MovieWrapper getNowPlayingMovie();

    List<MovieSearchDTO> getSearchedMovies(String keyword);
}
