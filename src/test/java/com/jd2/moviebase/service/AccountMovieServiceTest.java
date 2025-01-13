package com.jd2.moviebase.service;

import com.jd2.moviebase.dto.AccountMovieDto;
import com.jd2.moviebase.dto.MovieDto;
import com.jd2.moviebase.model.*;
import com.jd2.moviebase.repository.AccountMovieRepository;
import com.jd2.moviebase.util.ConstantsHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountMovieServiceTest {

    @Mock
    private AccountMovieRepository accountMovieRepository;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @Mock
    private MovieService movieService;

    @InjectMocks
    private AccountMovieService accountMovieService;

    @Test
    void create_ShouldCreateAccountMovieSuccessfully() {
        MovieDto inputMovieDto = MovieDto.builder().name("Test Movie").build();
        MovieDto createdMovieDto = MovieDto.builder().id(1L).name("Test Movie").build();
        AccountMovie accountMovie = getAccountMovie();
        AccountMovieDto expectedAccountMovieDto = getAccountMovieDto();

        UserDetailModel userDetailModel = new UserDetailModel(User.builder().role("ADMIN").build(), 1L);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetailModel);
        SecurityContextHolder.setContext(securityContext);

        when(movieService.createIfNotExist(any(MovieDto.class))).thenReturn(createdMovieDto);
        when(accountMovieRepository.save(any(AccountMovie.class))).thenReturn(accountMovie);

        AccountMovieDto result = accountMovieService.create(inputMovieDto);

        assertNotNull(result);
        assertEquals(expectedAccountMovieDto.getAccountId(), result.getAccountId());
        assertEquals(expectedAccountMovieDto.getMovieId(), result.getMovieId());
        assertEquals(expectedAccountMovieDto.getStatus(), result.getStatus());
        verify(movieService, times(1)).createIfNotExist(inputMovieDto);
        verify(accountMovieRepository, times(1)).save(any(AccountMovie.class));
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
    void updateStatusByAccountId_ShouldCallRepositoryUpdateStatusByAccId() {
        when(accountMovieRepository.updateStatusByAccountId(1L, 1L, ConstantsHelper.MovieStatus.WATCHED.toString()))
                .thenReturn(1);

        accountMovieService.updateStatusByAccId(1L, 1L, ConstantsHelper.MovieStatus.WATCHED);

        verify(accountMovieRepository, times(1))
                .updateStatusByAccountId(1L, 1L, ConstantsHelper.MovieStatus.WATCHED.toString());
    }

    @Test
    void deleteByAccountId_ShouldCallRepositoryDeleteByAccId() {
        doNothing().when(accountMovieRepository).deleteByAccountId(1L);

        accountMovieService.deleteByAccId(1L);

        verify(accountMovieRepository, times(1)).deleteByAccountId(1L);
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