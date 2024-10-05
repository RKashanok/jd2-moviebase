package com.jd2.moviebase.controller;

import com.jd2.moviebase.model.Genre;
import com.jd2.moviebase.service.GenreService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return genreService.create(genre);
    }

    @PutMapping("/{id}")
    public Genre update(@PathVariable ("id") long id, @RequestBody Genre genre) {
        return genreService.update(id, genre);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") long id) {
        genreService.deleteById(id);

    }
}

