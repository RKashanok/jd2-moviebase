package com.jd2.moviebase.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
@Data
@Builder
public class MovieDto {
    private Long id;
    private Long tmdbId;
    private String name;
    private List<Long> genreId;
    private LocalDate releaseDate;
    private Long rating;
    private String overview;
    private String originalLanguage;
}
