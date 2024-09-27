package com.jd2.moviebase.service;

import com.jd2.moviebase.dto.GenreDTO;
import com.jd2.moviebase.model.GenericMapper;
import com.jd2.moviebase.model.Genre;
import com.jd2.moviebase.repository.GenreRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
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

    public List<GenreDTO> findAll() {
        logger.info("Executing method: findAll()");
        List<Genre> genres = genreRepository.findAll();
        return genres.stream()
                .map(GenericMapper::genreToDto)
                .collect(Collectors.toList());
    }

    public GenreDTO findById(int id) {
        logger.info("Executing method: findById(id={})", id);
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Genre not found with id: " + id));
        return GenericMapper.genreToDto(genre);

    }

    public GenreDTO create(GenreDTO genreDTO) {
        logger.info("Executing method: create(genre={})", genreDTO);
        Genre genre = GenericMapper.genreDtoToModel(genreDTO);
        Genre createdGenre = genreRepository.create(genre);
        return GenericMapper.genreToDto(createdGenre);
    }

    public GenreDTO update(GenreDTO genreDTO) {
        logger.info("Executing method: update(genre={})", genreDTO);
        Genre genre = GenericMapper.genreDtoToModel(genreDTO);
        Genre updatedGenre = genreRepository.update(genre);
        return GenericMapper.genreToDto(updatedGenre);
    }

    public void deleteById(int id) {
        logger.info("Executing method: deleteByID(id={})", id);
        genreRepository.deleteById(id);
    }

}
