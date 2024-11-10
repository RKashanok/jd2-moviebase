package com.jd2.moviebase.service;

import com.jd2.moviebase.dto.AccountMovieDto;
import com.jd2.moviebase.model.AccountMovie;
import com.jd2.moviebase.repository.AccountMovieRepository;
import com.jd2.moviebase.util.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.jd2.moviebase.util.ConstantsHelper.MovieStatus;

@Service
public class AccountMovieService {
    private static final Logger logger = LoggerFactory.getLogger(AccountMovieService.class);
    private final AccountMovieRepository accountMovieRepository;


    @Autowired
    public AccountMovieService(AccountMovieRepository accountMovieRepository) {
        this.accountMovieRepository = accountMovieRepository;
    }

    public void create(AccountMovieDto accountMovieDto) {
        logger.info("Creating account movie: {}", accountMovieDto);
        accountMovieRepository.create(ModelMapper.toAccountMovie(accountMovieDto));
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
