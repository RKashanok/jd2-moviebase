package com.jd2.moviebase.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Genre {
    private int id;
    private int tmdbId;
    private String name;
}
