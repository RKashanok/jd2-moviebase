package com.jd2.moviebase.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jd2.moviebase.dto.MovieDto;
import com.jd2.moviebase.model.api.SearchMovieRequestParams;
import com.jd2.moviebase.model.api.SearchMovieResponse;
import lombok.RequiredArgsConstructor;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class SearchMovieClient {
    private final OkHttpClient okHttpClient;
    private final ObjectMapper objectMapper;
    private final String baseUrl = "https://api.themoviedb.org/3";
    private final String searchMovieEndpoint = "/search/movie";
    private final String token = System.getenv("TMDB_TOKEN");

    public MovieDto searchMovie(SearchMovieRequestParams searchMovieParams) {
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
            SearchMovieResponse.Movie movie = searchMovieResponse.getResults().get(0);
            return MovieDto.builder()
                    .tmdbId(movie.getId())
                    .name(movie.getTitle())
                    .genreId(new ArrayList<>(List.of(1)))
                    .releaseDate(Date.valueOf(movie.getRelease_date()))
                    .rating((int) movie.getVote_average())
                    .overview(movie.getOverview())
                    .originalLanguage(movie.getOriginal_language())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
