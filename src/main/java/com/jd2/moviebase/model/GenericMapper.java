package com.jd2.moviebase.model;

import com.jd2.moviebase.dto.GenreDTO;

public class GenericMapper {

    public static GenreDTO genreToDto(Genre genre) {
        GenreDTO genreDTO = new GenreDTO();
        genreDTO.setId(genre.getId());
        genreDTO.setTmdbId(genre.getTmdbId());
        genreDTO.setName(genre.getName());
        return genreDTO;
    }

    public static Genre genreDtoToModel(GenreDTO genreDTO) {
        Genre genre = new Genre();
        genre.setId(genreDTO.getId());
        genre.setTmdbId(genreDTO.getTmdbId());
        genre.setName(genreDTO.getName());
        return genre;
    }
}
