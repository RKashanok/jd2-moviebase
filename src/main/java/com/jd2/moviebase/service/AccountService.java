package com.jd2.moviebase.service;


import com.jd2.moviebase.config.DataSource;
import com.jd2.moviebase.model.Account;
import com.jd2.moviebase.repository.AccountRepository;
import com.jd2.moviebase.repository.CommentsRepository;
import com.jd2.moviebase.repository.AccountMovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountService {
    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);
    DataSource ds;

    {
        try {
            ds = new DataSource();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private final AccountRepository accountRepository;
    private final CommentsService commentsService;
    private final AccountMovieService accountMovieService;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        this.commentsService = new CommentsService(new CommentsRepository(ds));
        this.accountMovieService = new AccountMovieService(new AccountMovieRepository(ds));
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
        commentsService.deactivateByAccId(id);

        // delete account_movie
        accountMovieService.deleteByAccId(id);

        // delete account
        accountRepository.deleteById(id);
    }
}
