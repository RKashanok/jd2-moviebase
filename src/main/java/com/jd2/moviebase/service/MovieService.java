package com.jd2.moviebase.service;

import com.jd2.moviebase.dto.MovieDTO;
import com.jd2.moviebase.mapper.MovieMapper;
import com.jd2.moviebase.model.GenericMapper;
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

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<MovieDTO> findAll() {
        logger.info("Executing method: findAll()");
        List<Movie> movies = movieRepository.findAll();
        return movies.stream()
                .map(GenericMapper::movieToDto)
                .toList();

    }

    public MovieDTO findById(int id) {
        logger.info("Executing method: findById(id={})", id);
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + id));
        return GenericMapper.movieToDto(movie);
    }

    public MovieDTO create(MovieDTO movieDTO){
        logger.info("Executing method: create(genre={})", movieDTO);
        Movie movie = GenericMapper.movieDtoToModel(movieDTO);
        Movie createdMovie = movieRepository.create(movie);
        return GenericMapper.movieToDto(createdMovie);
    }

    public MovieDTO update(MovieDTO movieDTO){
        logger.info("Executing method: update(genre={})", movieDTO);
        Movie movie = GenericMapper.movieDtoToModel(movieDTO);
        Movie updatedMovie = movieRepository.update(movie);
        return GenericMapper.movieToDto(updatedMovie);
    }

    public void deleteByID(int id){
        logger.info("Executing method: deleteByID(id={})", id);
        movieRepository.deleteById(id);
    }

}
