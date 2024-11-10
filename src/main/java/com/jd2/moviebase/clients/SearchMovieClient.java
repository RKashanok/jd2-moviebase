package com.jd2.moviebase.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jd2.moviebase.dto.MovieDto;
import com.jd2.moviebase.model.api.SearchMovieRequestParams;
import com.jd2.moviebase.model.api.SearchMovieResponse;
import lombok.RequiredArgsConstructor;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SearchMovieClient {
    private final OkHttpClient okHttpClient;
    private final ObjectMapper objectMapper;
    private final String baseUrl = "https://api.themoviedb.org/3";
    private final String searchMovieEndpoint = "/search/movie";
    private final String token = System.getenv("TMDB_TOKEN");

    public List<MovieDto> searchMovie(SearchMovieRequestParams searchMovieParams) {
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(baseUrl + searchMovieEndpoint)).newBuilder();
        urlBuilder.addQueryParameter("query", searchMovieParams.getQuery());
        urlBuilder.addQueryParameter("include_adult", String.valueOf(searchMovieParams.isIncludeAdult()));
        urlBuilder.addQueryParameter("language", searchMovieParams.getLanguage());
        urlBuilder.addQueryParameter("page", searchMovieParams.getPage());

        Request request = new Request.Builder()
                .url(urlBuilder.build().toString())
                .header("Authorization", String.format("Bearer %s", token))
                .build();
        try {
            String response = okHttpClient.newCall(request).execute().body().string();
            SearchMovieResponse searchMovieResponse = objectMapper.readValue(response, SearchMovieResponse.class);
            return mapMovies(searchMovieResponse.getResults());
        } catch (Exception e) {
            throw new RuntimeException("Error processing movie search", e);
        }
    }

    private List<MovieDto> mapMovies(List<SearchMovieResponse.Movie> results) {
        List<MovieDto> movieDtos = new ArrayList<>();
        try {
            for (SearchMovieResponse.Movie movie : results) {
                movieDtos.add(MovieDto.builder()
                        .tmdbId((long)movie.getId())
                        .name(!Objects.equals(movie.getTitle(), "") ? movie.getTitle() : "Unknown Title")
                        .genreId(new ArrayList<>(List.of(1L)))
                        .releaseDate(!Objects.equals(movie.getRelease_date(), "") ? LocalDate.parse(movie.getRelease_date()) : null)
                        .rating((long) (movie.getVote_average() != 0 ? movie.getVote_average() : 0))
                        .overview(!Objects.equals(movie.getOverview(), "") ? movie.getOverview() : "No overview available")
                        .originalLanguage(!Objects.equals(movie.getOriginal_language(), "") ? movie.getOriginal_language() : "Unknown Language")
                        .build());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error mapping movies", e);
        }
        return movieDtos;
    }
}
