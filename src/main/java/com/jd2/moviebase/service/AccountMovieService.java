package com.jd2.moviebase.service;

import com.jd2.moviebase.dto.AccountMovieDTO;
import com.jd2.moviebase.model.AccountMovie;
import com.jd2.moviebase.model.GenericMapper;
import com.jd2.moviebase.repository.AccountMovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.jd2.moviebase.util.ConstantsHelper.MovieStatus;

@Service
public class AccountMovieService {
    private static final Logger logger = LoggerFactory.getLogger(AccountMovieService.class);
    private final AccountMovieRepository accountMovieRepository;


    @Autowired
    public AccountMovieService(AccountMovieRepository accountMovieRepository) {
        this.accountMovieRepository = accountMovieRepository;
    }

    public void create(AccountMovieDTO accountMovieDTO) {
        logger.info("Creating account movie: {}", accountMovieDTO);
        accountMovieRepository.create(GenericMapper.accountMovieDtoToModel(accountMovieDTO));
    }

    public List<AccountMovieDTO> findAllByAccountId(int accountId) {
        logger.info("Finding all account movies by account id: {}", accountId);
        List<AccountMovie> accountMovies = accountMovieRepository.findAllByAccountId(accountId);
        List<AccountMovieDTO> accountMovieDTOS = accountMovies.stream()
                .map(GenericMapper::accountMovieToDto)
                .toList();

        return accountMovieDTOS;
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
