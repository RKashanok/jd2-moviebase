package com.jd2.moviebase.model;

import com.jd2.moviebase.dto.MovieDTO;

public class GenericMapper {

    public static MovieDTO movieToDto(Movie movie) {
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

    public static Movie movieDtoToModel(MovieDTO movieDTO) {
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
