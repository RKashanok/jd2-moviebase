package com.jd2.moviebase.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Genre {

    private Long id;
    private Long tmdbId;
    private String name;
}
