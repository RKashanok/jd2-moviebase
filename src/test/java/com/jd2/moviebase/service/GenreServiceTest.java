package com.jd2.moviebase.service;

import com.jd2.moviebase.dto.GenreDto;
import com.jd2.moviebase.exception.MovieDbRepositoryOperationException;
import com.jd2.moviebase.model.Genre;
import com.jd2.moviebase.repository.GenreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GenreServiceTest {

    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private GenreService genreService;

    @Test
    void findAll_ShouldReturnListOfGenreDtos() {
        when(genreRepository.findAll()).thenReturn(List.of(getGenre()));

        List<GenreDto> result = genreService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(getGenreDto().getId(), result.get(0).getId());
        verify(genreRepository, times(1)).findAll();
    }

    @Test
    void findById_ShouldReturnGenreDto_WhenGenreExists() {
        when(genreRepository.findById(1L)).thenReturn(Optional.of(getGenre()));

        GenreDto result = genreService.findById(1L);

        assertNotNull(result);
        assertEquals(getGenreDto().getId(), result.getId());
        verify(genreRepository, times(1)).findById(1L);
    }

    @Test
    void findById_ShouldThrowException_WhenGenreDoesNotExist() {
        when(genreRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(MovieDbRepositoryOperationException.class, () -> genreService.findById(1L));
        verify(genreRepository, times(1)).findById(1L);
    }

    @Test
    void create_ShouldReturnCreatedGenreDto() {
        when(genreRepository.create(any(Genre.class))).thenReturn(getGenre());

        GenreDto result = genreService.create(getGenreDto());

        assertNotNull(result);
        assertEquals(getGenreDto().getId(), result.getId());
        verify(genreRepository, times(1)).create(any(Genre.class));
    }

    @Test
    void update_ShouldReturnUpdatedGenreDto() {
        when(genreRepository.update(any(Genre.class))).thenReturn(getGenre());

        GenreDto result = genreService.update(1L, getGenreDto());

        assertNotNull(result);
        assertEquals(getGenreDto().getId(), result.getId());
        verify(genreRepository, times(1)).update(any(Genre.class));
    }

    @Test
    void deleteById_ShouldInvokeRepositoryDeleteById() {
        doNothing().when(genreRepository).deleteById(1L);

        genreService.deleteById(1L);

        verify(genreRepository, times(1)).deleteById(1L);
    }

    private Genre getGenre() {
        return Genre.builder()
                .id(1L)
                .tmdbId(100L)
                .name("Action")
                .build();
    }

    private GenreDto getGenreDto() {
        return GenreDto.builder()
                .id(1L)
                .tmdbId(100L)
                .name("Action")
                .build();
    }
}