package com.jd2.moviebase.service;

import com.jd2.moviebase.model.AccountMovie;
import com.jd2.moviebase.repository.AccountMovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.jd2.moviebase.util.ConstantsHelper.MovieStatus;

public class AccountMovieService {
    private static final Logger logger = LoggerFactory.getLogger(AccountMovieService.class);
    private final AccountMovieRepository accountMovieRepository;

    public AccountMovieService(AccountMovieRepository accountMovieRepository) {
        this.accountMovieRepository = accountMovieRepository;
    }

    public int create(AccountMovie accountMovie) {
        logger.info("Creating account movie: {}", accountMovie);
        return accountMovieRepository.create(accountMovie);
    }

    public List<AccountMovie> findAllByAccountId(int accountId) {
        logger.info("Finding all account movies by account id: {}", accountId);
        return accountMovieRepository.findAllByAccountId(accountId);
    }

    public void updateStatusByAccId(int accountId, int movieId, MovieStatus status) {
        logger.info("Updating account movie status by account id: {}", accountId);
        accountMovieRepository.updateStatusByAccId(accountId, movieId, status);
    }

    public void deleteByAccId(int id) {
        logger.info("Deleting user movie by account id: {}", id);
        accountMovieRepository.deleteByAccId(id);
    }
}
