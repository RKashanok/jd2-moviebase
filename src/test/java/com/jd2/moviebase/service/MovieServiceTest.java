package com.jd2.moviebase.service;

import com.jd2.moviebase.dto.MovieDto;
import com.jd2.moviebase.exception.MovieDbRepositoryOperationException;
import com.jd2.moviebase.model.Movie;
import com.jd2.moviebase.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    @Test
    void findAll_ShouldReturnListOfMovieDtos() {
        when(movieRepository.findAll()).thenReturn(List.of(getMovie()));

        List<MovieDto> result = movieService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(getMovieDto().getId(), result.get(0).getId());
        verify(movieRepository, times(1)).findAll();
    }

    @Test
    void findById_ShouldReturnMovieDto_WhenMovieExists() {
        when(movieRepository.findById(1L)).thenReturn(Optional.of(getMovie()));

        MovieDto result = movieService.findById(1L);

        assertNotNull(result);
        assertEquals(getMovieDto().getId(), result.getId());
        verify(movieRepository, times(1)).findById(1L);
    }

    @Test
    void findById_ShouldThrowException_WhenMovieDoesNotExist() {
        when(movieRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(MovieDbRepositoryOperationException.class, () -> movieService.findById(1L));
        verify(movieRepository, times(1)).findById(1L);
    }

    @Test
    void create_ShouldReturnCreatedMovieDto() {
        Movie movie = getMovie();
        MovieDto movieDto = getMovieDto();
        when(movieRepository.save(any(Movie.class))).thenReturn(movie);

        MovieDto result = movieService.create(movieDto);

        assertNotNull(result);
        assertEquals(movieDto.getId(), result.getId());
        verify(movieRepository, times(1)).save(movie);
    }

    @Test
    void update_ShouldReturnUpdatedMovieDto() {
        Movie movie = getMovie();
        MovieDto movieDto = getMovieDto();
        when(movieRepository.save(any(Movie.class))).thenReturn(movie);
        when(movieRepository.existsById(movieDto.getId())).thenReturn(true);

        MovieDto result = movieService.update(movieDto);

        assertNotNull(result);
        assertEquals(movieDto.getId(), result.getId());
        verify(movieRepository, times(1)).existsById(movieDto.getId());
        verify(movieRepository, times(1)).save(movie);
    }

    @Test
    void deleteByID_ShouldInvokeRepositoryDeleteById() {
        when(movieRepository.existsById(1L)).thenReturn(true);
        doNothing().when(movieRepository).deleteById(1L);

        movieService.deleteByID(1L);

        verify(movieRepository, times(1)).deleteById(1L);
        verify(movieRepository, times(1)).existsById(1L);
    }

    private Movie getMovie() {
        return Movie.builder()
                .id(1L)
                .tmdbId(100L)
                .name("Pulp Fiction")
                .releaseDate(LocalDate.of(1994, 7, 16))
                .rating(8L)
                .overview("Two bandits Vincent Vega and Jules Winfield...")
                .originalLanguage("en")
                .genres(Collections.emptyList())
                .build();
    }


    private MovieDto getMovieDto() {
        return MovieDto.builder()
                .id(1L)
                .tmdbId(100L)
                .name("Pulp Fiction")
                .releaseDate(LocalDate.of(1994, 7, 16))
                .rating(8L)
                .overview("Two bandits Vincent Vega and Jules Winfield...")
                .originalLanguage("en")
                .genreId(Collections.emptyList())
                .build();
    }
}