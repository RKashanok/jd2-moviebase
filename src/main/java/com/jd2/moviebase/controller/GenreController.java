package com.jd2.moviebase.controller;

import com.jd2.moviebase.model.Genre;
import com.jd2.moviebase.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return genreService.findById(id);
    }
    //create


    //update

    //delete_by_id
}
