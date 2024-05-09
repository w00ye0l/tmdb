package com.example.movietest.service;

import com.example.movietest.entity.GenresWrapper;
import com.example.movietest.entity.MovieWrapper;

public interface MovieService {

    GenresWrapper getGenre();

    MovieWrapper getPopularMovie();
}
