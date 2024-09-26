package com.jd2.moviebase.controller;

import com.jd2.moviebase.dto.MovieDTO;
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
    public List<MovieDTO> findAll() {
        return movieService.findAll();
    }

    @GetMapping("/{id}")
    public MovieDTO findById(@PathVariable("id") int id) {
        return movieService.findById(id);
    }

    @PostMapping
    public MovieDTO create(@RequestBody MovieDTO movieDTO) {
        return movieService.create(movieDTO);
    }

    @PutMapping
    public MovieDTO update(@RequestBody MovieDTO movieDTO) {
        return movieService.update(movieDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") int id) {
        movieService.deleteByID(id);
    }
}
