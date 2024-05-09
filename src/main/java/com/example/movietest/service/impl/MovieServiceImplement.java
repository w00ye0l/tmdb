package com.example.movietest.service.impl;

import com.example.movietest.entity.GenresWrapper;
import com.example.movietest.repository.MovieRepository;
import com.example.movietest.service.MovieService;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@JsonView
public class MovieServiceImplement implements MovieService {

    private final MovieRepository movieRepository;

    public MovieServiceImplement(MovieRepository movieRepository) {
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

                genres.getGenres().forEach(genre -> movieRepository.save(genre));

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
}
