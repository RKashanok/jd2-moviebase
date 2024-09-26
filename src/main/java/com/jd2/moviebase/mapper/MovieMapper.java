package com.jd2.moviebase.mapper;

import com.jd2.moviebase.dto.MovieDTO;
import com.jd2.moviebase.model.Movie;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {

    public MovieDTO toDto(Movie movie) {
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setId(movie.getId());
        movieDTO.setTmdbId(movie.getTmdbId());
        movieDTO.setName(movie.getName());
        movieDTO.setGenreId(movie.getGenreId());
        movieDTO.setReleaseDate(movie.getReleaseDate());
        movieDTO.setRating(movie.getRating());
        movieDTO.setOverview(movie.getOverview());
        movieDTO.setOriginalLanguage(movie.getOriginalLanguage());
        return movieDTO;
    }

    public Movie toModel(MovieDTO movieDTO) {
        Movie movie = new Movie();
        movie.setId(movieDTO.getId());
        movie.setTmdbId(movieDTO.getTmdbId());
        movie.setName(movieDTO.getName());
        movie.setGenreId(movieDTO.getGenreId());
        movie.setReleaseDate(movieDTO.getReleaseDate());
        movie.setRating(movieDTO.getRating());
        movie.setOverview(movieDTO.getOverview());
        movie.setOriginalLanguage(movieDTO.getOriginalLanguage());
        return movie;
    }

}
