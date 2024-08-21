package com.service;

import com.moviebase.beans.Account;
import com.repository.AccountRepository;
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

    public Account update(Account account) {
        logger.info("Updating account: {}", account);
        return accountRepository.update(account);
    }

    public void deleteById(int id) {
        logger.info("Deleting account by id: {}", id);
        accountRepository.deleteById(id);
    }
}
