package com.jd2.moviebase.service;

import com.jd2.moviebase.dto.AccountMovieDto;
import com.jd2.moviebase.model.Account;
import com.jd2.moviebase.model.AccountMovie;
import com.jd2.moviebase.model.Movie;
import com.jd2.moviebase.repository.AccountMovieRepository;
import com.jd2.moviebase.util.ConstantsHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountMovieServiceTest {

    @Mock
    private AccountMovieRepository accountMovieRepository;

    @InjectMocks
    private AccountMovieService accountMovieService;

    @Test
    void create_ShouldCallRepositoryCreateMethod() {
        AccountMovie accountMovie = getAccountMovie();
        doNothing().when(accountMovieRepository).create(accountMovie);

        accountMovieService.create(getAccountMovieDto());

        verify(accountMovieRepository, times(1)).create(accountMovie);
    }

    @Test
    void findAllByAccountId_ShouldReturnListOfAccountMovieDtos() {
        when(accountMovieRepository.findAllByAccountId(1L)).thenReturn(List.of(getAccountMovie()));

        List<AccountMovieDto> result = accountMovieService.findAllByAccountId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(getAccountMovieDto().getStatus(), result.get(0).getStatus());
        verify(accountMovieRepository, times(1)).findAllByAccountId(1L);
    }

    @Test
    void updateStatusByAccId_ShouldCallRepositoryUpdateStatusByAccId() {
        doNothing().when(accountMovieRepository).updateStatusByAccId(1L, 1L, ConstantsHelper.MovieStatus.WATCHED);

        accountMovieService.updateStatusByAccId(1L, 1L, ConstantsHelper.MovieStatus.WATCHED);

        verify(accountMovieRepository, times(1)).updateStatusByAccId(1L, 1L, ConstantsHelper.MovieStatus.WATCHED);
    }

    @Test
    void deleteByAccId_ShouldCallRepositoryDeleteByAccId() {
        doNothing().when(accountMovieRepository).deleteByAccId(1L);

        accountMovieService.deleteByAccId(1L);

        verify(accountMovieRepository, times(1)).deleteByAccId(1L);
    }

    private AccountMovie getAccountMovie() {
        return AccountMovie.builder()
                .account(Account.builder().id(1L).build())
                .movie(Movie.builder().id(1L).build())
                .status("WATCHED")
                .build();
    }

    private AccountMovieDto getAccountMovieDto() {
        return AccountMovieDto.builder()
                .accountId(1L)
                .movieId(1L)
                .status("WATCHED")
                .build();
    }
}