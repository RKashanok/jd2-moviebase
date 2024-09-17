package com.jd2.moviebase.service;

import com.jd2.moviebase.model.Account;
import com.jd2.moviebase.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);
    private final AccountRepository accountRepository;
    private final CommentService commentService;
    private final AccountMovieService accountMovieService;

    @Autowired
    public AccountService(AccountRepository accountRepository, CommentService commentService, AccountMovieService accountMovieService) {
        this.accountRepository = accountRepository;
        this.commentService = commentService;
        this.accountMovieService = accountMovieService;
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

        // deactivate comments and set null account id
        commentService.deactivateByAccId(id);

        // delete account_movie
        accountMovieService.deleteByAccId(id);

        // delete account
        accountRepository.deleteById(id);
    }
}
