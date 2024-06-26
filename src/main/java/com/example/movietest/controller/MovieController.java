package com.example.movietest.controller;

import com.example.movietest.dto.MovieDetailDTO;
import com.example.movietest.dto.MovieSearchDTO;
import com.example.movietest.entity.GenresWrapper;
import com.example.movietest.entity.MovieWrapper;
import com.example.movietest.service.MovieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/now-playing")
    public MovieWrapper nowPlayingMovie() {
        return movieService.getNowPlayingMovie();
    }

    @GetMapping("/search")
    public List<MovieSearchDTO> searchMovies(@RequestParam String keyword) {
        System.out.println("keyword = " + keyword);

        return movieService.getSearchedMovies(keyword);
    }

    @GetMapping("/{movieId}")
    public MovieDetailDTO getMovie(@PathVariable Long movieId) {
        return movieService.getMovieDetail(movieId);
    }
}
