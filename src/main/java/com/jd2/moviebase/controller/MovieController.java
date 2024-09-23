package com.jd2.moviebase.controller;

import com.jd2.moviebase.model.Movie;
import com.jd2.moviebase.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public List<Movie> findAll() {
        return movieService.findAll();
    }

    @GetMapping("/{id}")
    public Movie findById(@PathVariable("id") int id) {
        return movieService.findById(id);
    }

    @PostMapping
    public Movie create(@RequestBody Movie movie) {
        return movieService.create(movie);
    }

    @PutMapping
    public Movie update(@RequestBody Movie movie) {
        return movieService.update(movie);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") int id) {
        movieService.deleteByID(id);
    }
}
