package com.jd2.moviebase.controller;

import com.jd2.moviebase.model.Genre;
import com.jd2.moviebase.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/genres")
public class GenreController {

    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/find_all")
    public @ResponseBody List<Genre> findAll() {
        return genreService.findAll();
    }

    @GetMapping("/find_by_id")
    public @ResponseBody Genre fidById(@RequestParam("id") int id) {
        Optional<Genre> optionalGenre = genreService.findById(id);
        return optionalGenre.orElse(null);
    }

    @PostMapping("/create")
    public @ResponseBody Genre create(@RequestBody Genre genre) {
        return genreService.create(genre);
    }

    @PutMapping("/update")
    public @ResponseBody Genre update(@RequestBody Genre genre) {
        Optional<Genre> existingGenre = genreService.findById(genre.getId());
        return existingGenre.map(g -> genreService.update(genre)).orElse(null);
    }

    @DeleteMapping("/delete_by_id")
    public void deleteById(@RequestParam("id") int id) {
        Optional<Genre> existingGenre = genreService.findById(id);
        existingGenre.ifPresent(g -> genreService.deleteByID(id));
    }
}

