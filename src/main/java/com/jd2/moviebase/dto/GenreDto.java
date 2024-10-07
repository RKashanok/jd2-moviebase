package com.jd2.moviebase.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenreDto {
    private int id;
    private int tmdbId;
    private String name;

}
