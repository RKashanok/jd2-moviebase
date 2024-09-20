package com.jd2.moviebase.service;

import com.jd2.moviebase.model.Genre;
import com.jd2.moviebase.repository.GenreRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService {

    private static final Logger logger = LoggerFactory.getLogger(GenreService.class);

    private final GenreRepository genreRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<Genre> findAll() {
        logger.info("Executing method: findAll()");
        List<Genre> genres = genreRepository.findAll();
        if(genres.isEmpty()){
            throw new RuntimeException("No genres found in the database.");
        }
        return genres;
    }

    public Genre findById(int id) {
        logger.info("Executing method: findById(id={})", id);
        return genreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Genre not found with id: " + id));
    }

    public Genre create(Genre genre){
        logger.info("Executing method: create(genre={})", genre);
        return genreRepository.create(genre);
    }

    public Genre update(Genre genre){
        logger.info("Executing method: update(genre={})", genre);
        return genreRepository.update(genre);
    }

    public void deleteById(int id){
        logger.info("Executing method: deleteByID(id={})", id);
        genreRepository.deleteById(id);
    }

}
