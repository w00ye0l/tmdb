package com.example.movietest.controller;

import com.example.movietest.entity.GenresWrapper;
import com.example.movietest.entity.MovieWrapper;
import com.example.movietest.service.MovieService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movie")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/genre")
    public GenresWrapper genre() {
        return movieService.getGenre();
    }

    @GetMapping("/popular")
    public MovieWrapper popularMovie() {
        return movieService.getPopularMovie();
    }
}
