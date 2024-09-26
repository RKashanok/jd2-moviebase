package com.jd2.moviebase.dto;

import lombok.Data;

import java.sql.Date;
import java.util.List;
@Data
public class MovieDTO {
    private int id;
    private int tmdbId;
    private String name;
    private List<Integer> genreId;
    private Date releaseDate;
    private int rating;
    private String overview;
    private String originalLanguage;
}
