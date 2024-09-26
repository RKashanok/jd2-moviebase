package com.jd2.moviebase.service;

import com.jd2.moviebase.dto.AccountDto;
import com.jd2.moviebase.model.Account;
import com.jd2.moviebase.repository.AccountRepository;
import com.jd2.moviebase.util.ModelMapper;
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

    public AccountDto create(Account account) {
        logger.info("Creating account: {}", account);
        return ModelMapper.mapObject(accountRepository.create(account), AccountDto.class);
    }

    public AccountDto findById(int id) {
        logger.info("Finding account by id: {}", id);
        return accountRepository.findById(id);
    }

    public AccountDto findByUserId(int userId) {
        logger.info("Finding account by user_id: {}", userId);
        return accountRepository.findByUserId(userId);
    }

    public AccountDto update(int id, AccountDto accountDto) {
        logger.info("Updating account: {}", accountDto);
        return accountRepository.update(id, accountDto);
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
