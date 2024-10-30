package com.jd2.moviebase.controller;

import com.jd2.moviebase.dto.MovieDto;
import com.jd2.moviebase.service.MovieService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public List<MovieDto> findAll() {
        return movieService.findAll();
    }

    @GetMapping("/{id}")
    public MovieDto findById(@PathVariable("id") Long id) {
        return movieService.findById(id);
    }

    @PostMapping
    public MovieDto create(@RequestBody MovieDto movieDTO) {
        return movieService.create(movieDTO);
    }

    @PutMapping
    public MovieDto update(@RequestBody MovieDto movieDTO) {
        return movieService.update(movieDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        movieService.deleteByID(id);
    }
}
