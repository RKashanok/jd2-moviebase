package com.jd2.moviebase.controller;

import com.jd2.moviebase.dto.AccountDto;
import com.jd2.moviebase.model.Account;
import com.jd2.moviebase.service.AccountService;
import com.jd2.moviebase.util.ModelMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public AccountDto findById(@PathVariable("id") Integer id) {
        return accountService.findById(id);
    }

    @GetMapping
    public AccountDto findByUserId(@RequestParam(name = "userId", required = false) Integer id) {
        return accountService.findByUserId(id);
    }

    @PostMapping
    public AccountDto create(@RequestBody Account account) {
        return ModelMapperUtil.mapObject(accountService.create(account), AccountDto.class);
    }

    @PutMapping("/{id}")
    public AccountDto update(@PathVariable("id") Integer id, @RequestBody AccountDto accountDto) {
        return accountService.update(id, accountDto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Integer id) {
        accountService.deleteById(id);
    }
}
