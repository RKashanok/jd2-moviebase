package com.jd2.moviebase.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Genre {
    private int id;
    private int tmdbId;
    private String name;
}
