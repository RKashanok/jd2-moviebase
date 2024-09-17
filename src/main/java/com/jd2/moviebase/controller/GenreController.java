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
    public ResponseEntity<List<Genre>> findAll() {
        List<Genre> genres = genreService.findAll();

        return new ResponseEntity<>(genres, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genre> findById(@PathVariable("id") int id) {
        Optional<Genre> optionalGenre = genreService.findById(id);

        return optionalGenre.map(g -> new ResponseEntity<>(g, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Genre> create(@RequestBody Genre genre) {
        return new ResponseEntity<>(genreService.create(genre), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Genre> update(@RequestBody Genre genre) {
        Optional<Genre> existingGenre = genreService.findById(genre.getId());

        return existingGenre.map(g -> new ResponseEntity<>(genreService.update(genre), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") int id) {
        genreService.deleteById(id);

    }
}

