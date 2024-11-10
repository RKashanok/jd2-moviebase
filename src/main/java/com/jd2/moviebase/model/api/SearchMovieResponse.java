package com.jd2.moviebase.model.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchMovieResponse {
    private int page;
    private List<Movie> results;

    @Getter
    public static class Movie {
        private boolean adult;
        private String backdrop_path;
        private List<Integer> genre_ids;
        private int id;
        private String original_language;
        private String original_title;
        private String overview;
        private double popularity;
        private String poster_path;
        private String release_date;
        private String title;
        private boolean video;
        private double vote_average;
        private int vote_count;
    }
}
