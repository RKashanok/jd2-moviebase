package com.jd2.moviebase.controller;

import com.jd2.moviebase.dto.GenreDTO;
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
    public List<GenreDTO> findAll() {
        return genreService.findAll();
    }

    @GetMapping("/{id}")
    public GenreDTO findById(@PathVariable("id") int id) {
        return genreService.findById(id);
    }

    @PostMapping
    public GenreDTO create(@RequestBody GenreDTO genreDTO) {
        return genreService.create(genreDTO);
    }

    @PutMapping
    public GenreDTO update(@RequestBody GenreDTO genreDTO) {
        return genreService.update(genreDTO);

    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") int id) {
        genreService.deleteById(id);

    }
}

