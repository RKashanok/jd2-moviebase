package com.jd2.moviebase.service;

import com.jd2.moviebase.config.DataSource;
import com.jd2.moviebase.model.Account;
import com.jd2.moviebase.repository.AccountRepository;
import com.jd2.moviebase.repository.CommentsRepository;
import com.jd2.moviebase.repository.UserMovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountService {
    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account create(Account account) {
        logger.info("Creating account: {}", account);
        return accountRepository.create(account);
    }

    public Account findById(int id) {
        logger.info("Finding account by id: {}", id);
        return accountRepository.findById(id);
    }

    public Account findByUserId(int userId) {
        logger.info("Finding account by user_id: {}", userId);
        return accountRepository.findByUserId(userId);
    }

    public Account update(Account account) {
        logger.info("Updating account: {}", account);
        return accountRepository.update(account);
    }

    public void deleteById(int id) {
        logger.info("Deleting account by id: {}", id);
        DataSource ds = new DataSource();

        // deactivate comments
        CommentsService commentsService = new CommentsService(new CommentsRepository(ds));
        commentsService.deactivateByAccId(id);

        // delete user_movie
        UserMovieService userMovieService = new UserMovieService(new UserMovieRepository(ds));
        userMovieService.deleteByAccId(id);

        // delete account
        accountRepository.deleteById(id);
    }
}
