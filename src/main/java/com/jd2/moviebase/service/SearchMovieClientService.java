package com.jd2.moviebase.service;

import com.jd2.moviebase.clients.SearchMovieClient;
import com.jd2.moviebase.model.api.SearchMovieParams;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchMovieClientService {
    private final SearchMovieClient searchMovieClient;

    @Autowired
    public SearchMovieClientService(SearchMovieClient searchMovieClient) {
        this.searchMovieClient = searchMovieClient;
    }

    public String searchMovie(SearchMovieParams searchMovieParams) {
        Response response = searchMovieClient.searchMovie(searchMovieParams);

        try {
            if (response.isSuccessful() && response.body() != null) {
                return response.body().string();
            } else {
                throw new RuntimeException("Failed to search movie: " + response.message());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error processing movie search", e);
        }
    }
}
