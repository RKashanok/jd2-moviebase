package com.jd2.moviebase.service;

import com.jd2.moviebase.repository.UserMovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserMovieService {
    private static final Logger logger = LoggerFactory.getLogger(UserMovieService.class);
    private final UserMovieRepository userMovieRepository;

    public UserMovieService(UserMovieRepository userMovieRepository) {
        this.userMovieRepository = userMovieRepository;
    }

    public void deleteByAccId(int id) {
        logger.info("Deleting user movie by account id: {}", id);
        userMovieRepository.deleteByAccId(id);
    }
}
