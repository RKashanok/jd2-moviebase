package com.jd2.moviebase.service;

import com.jd2.moviebase.model.Genre;
import com.jd2.moviebase.repository.GenreRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {

    private static final Logger logger = LoggerFactory.getLogger(GenreService.class);

    private final GenreRepository genreRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<Genre> findAll() {
        logger.info("findAll log from {}", GenreService.class.getSimpleName());
        return genreRepository.findAll();
    }

    public Genre findById(Genre genre) {
        logger.info("findById log from {}", GenreService.class.getSimpleName());
        return genreRepository.findById(genre);
    }

    public Genre create(Genre genre){
        logger.info("create log from {}", GenreService.class.getSimpleName());
        return genreRepository.create(genre);
    }

    public Genre update(Genre genre){
        logger.info("update log from {}", GenreService.class.getSimpleName());
        return genreRepository.update(genre);
    }

    public Genre deleteByID(Genre genre){
        logger.info("deleteByID log from {}", GenreService.class.getSimpleName());
        return genreRepository.deleteById(genre);
    }

}
