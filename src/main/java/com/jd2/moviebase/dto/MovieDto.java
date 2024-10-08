package com.jd2.moviebase.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;
import java.util.List;
@Data
@Builder
public class MovieDto {
    private int id;
    private int tmdbId;
    private String name;
    private List<Integer> genreId;
    private Date releaseDate;
    private int rating;
    private String overview;
    private String originalLanguage;
}
