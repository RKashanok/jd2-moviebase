package com.jd2.moviebase.mapper;

import com.jd2.moviebase.dto.GenreDTO;
import com.jd2.moviebase.model.Genre;
import org.springframework.stereotype.Component;

@Component
public class GenreMapper {

    public GenreDTO toDto(Genre genre) {
        if (genre == null) {
            return null;
        }
        GenreDTO genreDTO = new GenreDTO();
        genreDTO.setId(genre.getId());
        genreDTO.setTmdbId(genre.getTmdbId());
        genreDTO.setName(genre.getName());
        return genreDTO;
    }

    public Genre toModel(GenreDTO genreDTO) {
        if (genreDTO == null) {
            return null;
        }
        Genre genre = new Genre();
        genre.setId(genreDTO.getId());
        genre.setTmdbId(genreDTO.getTmdbId());
        genre.setName(genreDTO.getName());
        return genre;
    }
}
