package com.jd2.moviebase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
