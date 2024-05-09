package com.example.movietest.service.impl;

import com.example.movietest.dto.MovieSearchDTO;
import com.example.movietest.entity.GenresWrapper;
import com.example.movietest.entity.Movie;
import com.example.movietest.entity.MovieWrapper;
import com.example.movietest.repository.GenreRepository;
import com.example.movietest.repository.MovieRepository;
import com.example.movietest.service.MovieService;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@JsonView
public class MovieServiceImplement implements MovieService {

    private final GenreRepository genreRepository;
    private final MovieRepository movieRepository;

    public MovieServiceImplement(GenreRepository genreRepository, MovieRepository movieRepository) {
        this.genreRepository = genreRepository;
        this.movieRepository = movieRepository;
    }

    /**
     * TMDB API를 통해서 장르 리스트를 가져오는 서비스,
     * 장르 리스트를 DB에 저장
     *
     * @return List[Genre]
     */
    public GenresWrapper getGenre() {
        OkHttpClient client = new OkHttpClient();

        Response response = null;
        try {
            Request request = new Request.Builder()
                    .url("https://api.themoviedb.org/3/genre/movie/list?language=en")
                    .get()
                    .addHeader("accept", "application/json")
                    .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhZmM4MjY2Nzg0NWIwOWMxYzM3YmQxNjZhYmQwYzgxMiIsInN1YiI6IjYyZGQwMjkxZWE4NGM3MDA0Y2RiYTczNSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Nqz24p3GBP1aBLFIf7nrsCmvIbK7CUnCjYADaUWcVHY")
                    .build();

            response = client.newCall(request).execute();

            // 응답 성공 확인
            if (response.isSuccessful() && response.body() != null) {
                String responseData = response.body().string(); // 응답 데이터를 문자열로 변환
                response.body().close(); // 자원 해제

                ObjectMapper objectMapper = new ObjectMapper();

                GenresWrapper genres = objectMapper.readValue(responseData, GenresWrapper.class);

                genreRepository.saveAll(genres.getGenres());

                return genres;
            } else {
                System.out.println("Request failed with status: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.close(); // 자원 해제
            }
        }

        return null;
    }

    /**
     * TMDB API를 통해서 인기 있는 영화 리스트를 가져오는 서비스,
     * 페이지를 반복문을 통해서 조회,
     * 영화 리스트를 DB에 저장
     *
     * @return List[Movie]
     */
    public MovieWrapper getPopularMovie() {
        OkHttpClient client = new OkHttpClient();

        Response response = null;

        // 1페이지만 일단 조회
        for (int i = 1; i < 2; i++) {
            try {

                Request request = new Request.Builder()
                        .url("https://api.themoviedb.org/3/movie/popular?language=en-US&page=" + i)
                        .get()
                        .addHeader("accept", "application/json")
                        .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhZmM4MjY2Nzg0NWIwOWMxYzM3YmQxNjZhYmQwYzgxMiIsInN1YiI6IjYyZGQwMjkxZWE4NGM3MDA0Y2RiYTczNSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Nqz24p3GBP1aBLFIf7nrsCmvIbK7CUnCjYADaUWcVHY")
                        .build();

                response = client.newCall(request).execute();

                // 응답 성공 확인
                if (response.isSuccessful() && response.body() != null) {
                    String responseData = response.body().string(); // 응답 데이터를 문자열로 변환
                    response.body().close(); // 자원 해제

                    ObjectMapper objectMapper = new ObjectMapper();

                    try {
                        MovieWrapper popularMovies = objectMapper.readValue(responseData, MovieWrapper.class);

                        movieRepository.saveAll(popularMovies.getResults());

                        return popularMovies;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    System.out.println("Request failed with status: " + response.code());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (response != null) {
                    response.close(); // 자원 해제
                }
            }
        }

        return null;
    }

    /**
     * TMDB API를 통해서 현재 상영 중인 영화 리스트를 가져오는 서비스,
     * 페이지를 반복문을 통해서 조회,
     * 영화 리스트를 DB에 저장
     *
     * @return List[Movie]
     */
    public MovieWrapper getNowPlayingMovie() {
        OkHttpClient client = new OkHttpClient();

        Response response = null;

        // 1페이지만 일단 조회
        for (int i = 1; i < 2; i++) {
            try {

                Request request = new Request.Builder()
                        .url("https://api.themoviedb.org/3/movie/now_playing?language=en-US&page=" + i)
                        .get()
                        .addHeader("accept", "application/json")
                        .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhZmM4MjY2Nzg0NWIwOWMxYzM3YmQxNjZhYmQwYzgxMiIsInN1YiI6IjYyZGQwMjkxZWE4NGM3MDA0Y2RiYTczNSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Nqz24p3GBP1aBLFIf7nrsCmvIbK7CUnCjYADaUWcVHY")
                        .build();

                response = client.newCall(request).execute();

                // 응답 성공 확인
                if (response.isSuccessful() && response.body() != null) {
                    String responseData = response.body().string(); // 응답 데이터를 문자열로 변환
                    response.body().close(); // 자원 해제

                    ObjectMapper objectMapper = new ObjectMapper();

                    try {
                        MovieWrapper nowPlayingMovies = objectMapper.readValue(responseData, MovieWrapper.class);

                        List<Movie> movies = nowPlayingMovies.getResults();

                        for (Movie movie : movies) {
                            Long id = movie.getId();

                            if (!movieRepository.existsById(id)) {
                                // save와 다르게 실시간으로 DB 반영
                                movieRepository.saveAndFlush(movie);
                            }
                        }

                        return nowPlayingMovies;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    System.out.println("Request failed with status: " + response.code());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (response != null) {
                    response.close(); // 자원 해제
                }
            }
        }

        return null;
    }


    /**
     * 영화 검색 서비스,
     * 영화 제목에 키워드를 통해서 검색
     *
     * @return List[MovieSearchDTO]
     */
    public List<MovieSearchDTO> getSearchedMovies(String keyword) {

        List<Movie> findMovies = movieRepository.findAllByTitleContaining(keyword);
        List<MovieSearchDTO> searchedMovies = new ArrayList<>();

        for (Movie movie : findMovies) {
            System.out.println("movie = " + movie.getId());
            MovieSearchDTO movieSearchDTO = new MovieSearchDTO();

            movieSearchDTO.setId(movie.getId());
            movieSearchDTO.setTitle(movie.getTitle());
            movieSearchDTO.setRelease_date(movie.getRelease_date());

            searchedMovies.add(movieSearchDTO);
        }

        return searchedMovies;
    }
}
