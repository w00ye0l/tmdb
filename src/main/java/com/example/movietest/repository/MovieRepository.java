package com.example.movietest.repository;

import com.example.movietest.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findAllByTitleContaining(String keyword);
}
