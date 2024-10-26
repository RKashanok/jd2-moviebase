package com.jd2.moviebase.controller;

import com.jd2.moviebase.dto.ListWrapper;
import com.jd2.moviebase.dto.MovieDto;
import com.jd2.moviebase.model.api.SearchMovieRequestParams;
import com.jd2.moviebase.service.SearchMovieClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search/movie")
public class SearchMovieClientController {
    private SearchMovieClientService searchMovieClientService;

    @Autowired
    public SearchMovieClientController(SearchMovieClientService searchMovieClientService) {
        this.searchMovieClientService = searchMovieClientService;
    }


    @GetMapping()
    public ListWrapper searchMovie(@RequestParam("query") String query,
                                      @RequestParam(value = "include_adult", required = false, defaultValue = "false") String includeAdult,
                                      @RequestParam(value = "language", required = false, defaultValue = "en-US") String language,
                                      @RequestParam(value = "page", required = false, defaultValue = "1") String page) {
        SearchMovieRequestParams searchMovieParams = SearchMovieRequestParams.builder()
                .query(query)
                .includeAdult(Boolean.parseBoolean(includeAdult))
                .language(language)
                .page(page).build();
        List<MovieDto> result = searchMovieClientService.searchMovie(searchMovieParams);
        return new ListWrapper<>(result, result.size());
    }
}
