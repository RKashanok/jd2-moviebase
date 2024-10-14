package com.jd2.moviebase.service;

import com.jd2.moviebase.dto.MovieDto;
import com.jd2.moviebase.exception.MovieDbRepositoryOperationException;
import com.jd2.moviebase.model.Movie;
import com.jd2.moviebase.repository.MovieRepository;
import com.jd2.moviebase.util.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private static final Logger logger = LoggerFactory.getLogger(GenreService.class);

    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<MovieDto> findAll() {
        logger.info("Executing method: findAll()");
        List<Movie> movies = movieRepository.findAll();
        return movies.stream()
                .map(ModelMapper::toMovieDto)
                .collect(Collectors.toList());
    }

    public MovieDto findById(int id) {
        logger.info("Executing method: findById(id={})", id);
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new MovieDbRepositoryOperationException("Movie not found with id: " + id));
        return ModelMapper.toMovieDto(movie);
    }

    public MovieDto create(MovieDto movieDto){
        logger.info("Executing method: create(genre={})", movieDto);
        Movie movie = ModelMapper.toMovie(movieDto);
        Movie createdMovie = movieRepository.create(movie);
        return ModelMapper.toMovieDto(createdMovie);
    }

    public MovieDto update(MovieDto movieDto){
        logger.info("Executing method: update(genre={})", movieDto);
        Movie movie = ModelMapper.toMovie(movieDto);
        Movie updatedMovie = movieRepository.update(movie);
        return ModelMapper.toMovieDto(updatedMovie);
    }

    public void deleteByID(int id){
        logger.info("Executing method: deleteByID(id={})", id);
        movieRepository.deleteById(id);
    }

}
