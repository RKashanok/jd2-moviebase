package com.jd2.moviebase.service;

import com.jd2.moviebase.dto.GenreDto;
import com.jd2.moviebase.util.ModelMapper;
import com.jd2.moviebase.model.Genre;
import com.jd2.moviebase.repository.GenreRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreService {

    private static final Logger logger = LoggerFactory.getLogger(GenreService.class);

    private final GenreRepository genreRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<GenreDto> findAll() {
        logger.info("Executing method: findAll()");
        List<Genre> genres = genreRepository.findAll();
        return genres.stream()
                .map(ModelMapper::toGenreDto)
                .collect(Collectors.toList());
    }

    public GenreDto findById(int id) {
        logger.info("Executing method: findById(id={})", id);
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Genre not found with id: " + id));
        return ModelMapper.toGenreDto(genre);

    }

    public GenreDto create(GenreDto genreDto) {
        logger.info("Executing method: create(genre={})", genreDto);
        Genre genre = ModelMapper.toGenre(genreDto);
        Genre createdGenre = genreRepository.create(genre);
        return ModelMapper.toGenreDto(createdGenre);
    }

    public GenreDto update(GenreDto genreDto) {
        logger.info("Executing method: update(genre={})", genreDto);
        Genre genre = ModelMapper.toGenre(genreDto);
        Genre updatedGenre = genreRepository.update(genre);
        return ModelMapper.toGenreDto(updatedGenre);
    }

    public void deleteById(int id) {
        logger.info("Executing method: deleteByID(id={})", id);
        genreRepository.deleteById(id);
    }

}
