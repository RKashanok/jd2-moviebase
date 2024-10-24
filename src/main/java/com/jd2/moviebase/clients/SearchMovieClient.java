package com.jd2.moviebase.clients;

import com.jd2.moviebase.model.api.SearchMovieParams;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class SearchMovieClient {
    private final OkHttpClient okHttpClient;
    private final String baseUrl = "https://api.themoviedb.org/3";

    public SearchMovieClient(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    public Response searchMovie(SearchMovieParams searchMovieParams) {
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(baseUrl + "/search/movie")).newBuilder();
        urlBuilder.addQueryParameter("query", searchMovieParams.getQuery());
        urlBuilder.addQueryParameter("include_adult", String.valueOf(searchMovieParams.isIncludeAdult()));
        urlBuilder.addQueryParameter("language", searchMovieParams.getLanguage());
        urlBuilder.addQueryParameter("page", searchMovieParams.getPage());

        Request request = new Request.Builder()
                .url(urlBuilder.build().toString())
                .header("Authorization", String.format("Bearer %s", System.getenv("TMDB_TOKEN")))
                .build();

        try {
            return okHttpClient.newCall(request).execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
