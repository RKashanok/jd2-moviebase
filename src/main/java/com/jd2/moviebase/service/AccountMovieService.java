package com.jd2.moviebase.service;

import com.jd2.moviebase.dto.AccountMovieDto;
import com.jd2.moviebase.dto.MovieDto;
import com.jd2.moviebase.model.AccountMovie;
import com.jd2.moviebase.model.UserDetailModel;
import com.jd2.moviebase.repository.AccountMovieRepository;
import com.jd2.moviebase.util.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.jd2.moviebase.util.ConstantsHelper.MovieStatus;

@Service
public class AccountMovieService {
    private static final Logger logger = LoggerFactory.getLogger(AccountMovieService.class);
    private final AccountMovieRepository accountMovieRepository;
    private final MovieService movieService;


    @Autowired
    public AccountMovieService(AccountMovieRepository accountMovieRepository, MovieService movieService) {
        this.accountMovieRepository = accountMovieRepository;
        this.movieService = movieService;
    }

    public AccountMovieDto create(MovieDto movieDto) {
        UserDetailModel user = (UserDetailModel)SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        Long accountId = user.getAccountId();
        logger.info("Creating account movie {} for account {}", movieDto, accountId);

        MovieDto movieDtoResult = movieService.createIfNotExist(movieDto);

        AccountMovieDto accountMovieDto = AccountMovieDto.builder()
                .accountId(accountId)
                .movieId(movieDtoResult.getId())
                .status(String.valueOf(MovieStatus.TO_WATCH))
                .build();

        AccountMovie createdAccountMovie = accountMovieRepository.create(ModelMapper.toAccountMovie(accountMovieDto));
        return ModelMapper.toAccountMovieDto(createdAccountMovie);
    }

    public List<AccountMovieDto> findAllByAccountId(Long accountId) {
        logger.info("Finding all account movies by account id: {}", accountId);
        List<AccountMovie> accountMovies = accountMovieRepository.findAllByAccountId(accountId);
        List<AccountMovieDto> accountMovieDtos = accountMovies.stream()
                .map(ModelMapper::toAccountMovieDto)
                .collect(Collectors.toList());
        return accountMovieDtos;
    }

    public void updateStatusByAccId(Long accountId, Long movieId, MovieStatus status) {
        logger.info("Updating account movie status by account id: {}", accountId);
        accountMovieRepository.updateStatusByAccId(accountId, movieId, status);
    }

    public void deleteByAccId(Long id) {
        logger.info("Deleting user movie by account id: {}", id);
        accountMovieRepository.deleteByAccId(id);
    }
}
