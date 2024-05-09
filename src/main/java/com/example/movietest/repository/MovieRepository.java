package com.example.movietest.repository;

import com.example.movietest.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Genre, Long> {
}
