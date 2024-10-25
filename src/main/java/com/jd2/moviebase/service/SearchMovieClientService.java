package com.jd2.moviebase.service;

import com.jd2.moviebase.clients.SearchMovieClient;
import com.jd2.moviebase.dto.MovieDto;
import com.jd2.moviebase.model.api.SearchMovieRequestParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchMovieClientService {
    private final SearchMovieClient searchMovieClient;

    @Autowired
    public SearchMovieClientService(SearchMovieClient searchMovieClient) {
        this.searchMovieClient = searchMovieClient;
    }

    public MovieDto searchMovie(SearchMovieRequestParams searchMovieParams) {
        try {
            return searchMovieClient.searchMovie(searchMovieParams);
        } catch (Exception e) {
            throw new RuntimeException("Error processing movie search", e);
        }
    }
}
