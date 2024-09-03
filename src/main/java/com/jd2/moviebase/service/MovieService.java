package com.jd2.moviebase.service;

import com.jd2.moviebase.model.Movie;
import com.jd2.moviebase.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class MovieService {

    private static final Logger logger = LoggerFactory.getLogger(GenreService.class);

    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> findAll() {
        logger.info("Executing method: findAll()");
        return movieRepository.findAll();
    }

    public Optional<Movie> findById(int id) {
        logger.info("Executing method: findById(id={})", id);
        return movieRepository.findById(id);
    }

    public Movie create(Movie movie){
        logger.info("Executing method: create(genre={})", movie);
        return movieRepository.create(movie);
    }

    public Movie update(Movie movie){
        logger.info("Executing method: update(genre={})", movie);
        return movieRepository.update(movie);
    }

    public void deleteByID(int id){
        logger.info("Executing method: deleteByID(id={})", id);
        movieRepository.deleteById(id);
    }

}
