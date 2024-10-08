package com.jd2.moviebase.model;

import java.sql.Date;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Movie {

    private int id;
    private int tmdbId;
    private String name;
    private List<Integer> genreId;
    private Date releaseDate;
    private int rating;
    private String overview;
    private String originalLanguage;
}
