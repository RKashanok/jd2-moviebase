package com.jd2.moviebase.model.api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchMovieParams {
    private String query;
    private boolean includeAdult;
    private String language;
    private String page;
}
