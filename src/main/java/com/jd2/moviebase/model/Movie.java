package com.jd2.moviebase.model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Movie {

    private Long id;
    private Long tmdbId;
    private String name;
    private List<Long> genreId;
    private LocalDate releaseDate;
    private Long rating;
    private String overview;
    private String originalLanguage;
}
