package com.jd2.moviebase.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Genre {
    private int id;
    private int tmdbId;
    private String name;

}
