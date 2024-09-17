package com.jd2.moviebase.controller;

import com.jd2.moviebase.model.Account;
import com.jd2.moviebase.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{id}")
    public Account findById(@PathVariable("id") Integer id) {
        return accountService.findById(id);
    }

    @GetMapping("users/{id}")
    public Account findByUserId(@PathVariable("id") Integer id) {
        return accountService.findByUserId(id);
    }

    @PostMapping
    public Account create(@RequestBody Account account) {
        return accountService.create(account);
    }

    @PutMapping
    public Account update(@RequestBody Account account) {
        return accountService.update(account);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Integer id) {
        accountService.deleteById(id);
    }
}
