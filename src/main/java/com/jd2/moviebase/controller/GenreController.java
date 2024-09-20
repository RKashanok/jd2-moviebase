package com.jd2.moviebase.controller;

import com.jd2.moviebase.model.Genre;
import com.jd2.moviebase.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/genres")
public class GenreController {

    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public List<Genre> findAll() {
        return genreService.findAll();
    }

    @GetMapping("/{id}")
    public Genre findById(@PathVariable("id") int id) {
        return genreService.findById(id);
    }

    @PostMapping
    public Genre create(@RequestBody Genre genre) {
        return  genreService.create(genre);
    }

    @PutMapping
    public Genre update(@RequestBody Genre genre) {
        return genreService.update(genre);

    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") int id) {
        genreService.deleteById(id);

    }
}

