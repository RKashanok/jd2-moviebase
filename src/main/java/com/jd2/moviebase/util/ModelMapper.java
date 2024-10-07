package com.jd2.moviebase.util;

import com.jd2.moviebase.dto.GenreDto;
import com.jd2.moviebase.model.Genre;

public class ModelMapper {

    public static GenreDto toGenreDto(Genre genre) {
        return GenreDto.builder()
                .id(genre.getId())
                .tmdbId(genre.getTmdbId())
                .name(genre.getName())
                .build();
    }

    public static Genre toGenre(GenreDto genreDTO) {
        return Genre.builder()
                .id(genreDTO.getId())
                .tmdbId(genreDTO.getTmdbId())
                .name(genreDTO.getName())
                .build();
    }
}
