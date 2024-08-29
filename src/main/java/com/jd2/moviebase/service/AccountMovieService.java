package com.jd2.moviebase.service;

import com.jd2.moviebase.repository.AccountMovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountMovieService {
    private static final Logger logger = LoggerFactory.getLogger(AccountMovieService.class);
    private final AccountMovieRepository accountMovieRepository;

    public AccountMovieService(AccountMovieRepository accountMovieRepository) {
        this.accountMovieRepository = accountMovieRepository;
    }

    public void deleteByAccId(int id) {
        logger.info("Deleting user movie by account id: {}", id);
        accountMovieRepository.deleteByAccId(id);
    }
}
