package com.jd2.moviebase.service;

import com.jd2.moviebase.dto.MovieDTO;
import com.jd2.moviebase.mapper.MovieMapper;
import com.jd2.moviebase.model.Genre;
import com.jd2.moviebase.model.Movie;
import com.jd2.moviebase.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private static final Logger logger = LoggerFactory.getLogger(GenreService.class);

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    @Autowired
    public MovieService(MovieRepository movieRepository, MovieMapper movieMapper) {
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
    }

    public List<MovieDTO> findAll() {
        logger.info("Executing method: findAll()");
        List<Movie> movies = movieRepository.findAll();
        return movies.stream()
                .map(movieMapper::toDto)
                .toList();

    }

    public MovieDTO findById(int id) {
        logger.info("Executing method: findById(id={})", id);
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + id));
        return movieMapper.toDto(movie);
    }

    public MovieDTO create(MovieDTO movieDTO){
        logger.info("Executing method: create(genre={})", movieDTO);
        Movie movie = movieMapper.toModel(movieDTO);
        Movie createdMovie = movieRepository.create(movie);
        return movieMapper.toDto(createdMovie);
    }

    public MovieDTO update(MovieDTO movieDTO){
        logger.info("Executing method: update(genre={})", movieDTO);
        Movie movie = movieMapper.toModel(movieDTO);
        Movie updatedMovie = movieRepository.update(movie);
        return movieMapper.toDto(updatedMovie);
    }

    public void deleteByID(int id){
        logger.info("Executing method: deleteByID(id={})", id);
        movieRepository.deleteById(id);
    }

}
