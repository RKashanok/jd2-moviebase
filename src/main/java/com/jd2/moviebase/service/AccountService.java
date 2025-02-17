package com.jd2.moviebase.service;

import com.jd2.moviebase.dto.AccountDto;
import com.jd2.moviebase.exception.MovieDbRepositoryOperationException;
import com.jd2.moviebase.model.Account;
import com.jd2.moviebase.repository.AccountRepository;
import com.jd2.moviebase.util.ModelMapper;
import jakarta.transaction.Transactional;
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
    public AccountService(AccountRepository accountRepository, CommentService commentService,
                          AccountMovieService accountMovieService) {
        this.accountRepository = accountRepository;
        this.commentService = commentService;
        this.accountMovieService = accountMovieService;
    }

    public AccountDto create(AccountDto accountDto) {
        logger.info("Creating account: {}", accountDto);
        Account account = accountRepository.save(ModelMapper.toAccount(accountDto));
        return ModelMapper.toAccountDto(account);
    }

    public AccountDto findById(Long id) {
        logger.info("Finding account by id: {}", id);
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new MovieDbRepositoryOperationException("Account with ID " + id + " not found"));
        return ModelMapper.toAccountDto(account);
    }

    public AccountDto findByUserId(Long userId) {
        logger.info("Finding account by user_id: {}", userId);
        Account account = accountRepository.findByUserId(userId)
                .orElseThrow(() -> new MovieDbRepositoryOperationException("Account with user ID " + userId + " not found"));
        return ModelMapper.toAccountDto(account);
    }

    public AccountDto update(Long id, AccountDto accountDto) {
        logger.info("Updating account: {}", accountDto);

        if (!accountRepository.existsById(id)) {
            throw new MovieDbRepositoryOperationException("Account with ID " + id + " not found");
        }

        accountDto.setId(id);
        Account account = ModelMapper.toAccount(accountDto);
        return ModelMapper.toAccountDto(accountRepository.save(account));
    }

    @Transactional
    public void deleteById(Long id) {
        logger.info("Deleting account by id: {}", id);
        // deactivate comments and set null account id
        commentService.deactivateByAccId(id);
        // delete account_movie
        accountMovieService.deleteByAccId(id);
        // delete account
        accountRepository.deleteById(id);
    }
}
