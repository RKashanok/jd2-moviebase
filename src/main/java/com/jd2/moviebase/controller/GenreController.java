package com.jd2.moviebase.controller;

import com.jd2.moviebase.dto.GenreDto;
import com.jd2.moviebase.service.GenreService;
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
@RequestMapping("/genres")
public class GenreController {

    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public List<GenreDto> findAll() {
        return genreService.findAll();
    }

    @GetMapping("/{id}")
    public GenreDto findById(@PathVariable("id") Long id) {
        return genreService.findById(id);
    }

    @PostMapping
    public GenreDto create(@RequestBody GenreDto genreDTO) {
        return genreService.create(genreDTO);
    }

    @PutMapping("/{id}")
    public GenreDto update(@PathVariable("id") Long id, @RequestBody GenreDto genreDto) {
        return genreService.update(id, genreDto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        genreService.deleteById(id);
    }
}

