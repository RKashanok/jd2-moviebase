package com.jd2.moviebase.controller;

import com.jd2.moviebase.dto.AccountMovieDto;
import com.jd2.moviebase.service.AccountMovieService;
import com.jd2.moviebase.util.ConstantsHelper;
import java.util.List;
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
@RequestMapping
public class AccountMovieController {

    private AccountMovieService accountMovieService;

    @Autowired
    public AccountMovieController(AccountMovieService accountMovieService) {
        this.accountMovieService = accountMovieService;
    }

    @GetMapping("/account-movies/{accountId}")
    public List<AccountMovieDto> findByAccountId(@PathVariable(value = "accountId") int accountId) {
        return accountMovieService.findAllByAccountId(accountId);
    }

    @PostMapping("/account-movies")
    public void create(@RequestBody AccountMovieDto accountMovieDto) {
        accountMovieService.create(accountMovieDto);
    }

    // Обновление статуса account_movie по accountId и movieId
    @PutMapping("/account-movies/{accountId}/{movieId}")
    public void updateMovieStatus(@PathVariable(value = "accountId") int accountId,
        @PathVariable(value = "movieId") int movieId,
        @RequestBody ConstantsHelper.MovieStatus status) {
        accountMovieService.updateStatusByAccId(accountId, movieId, status);
    }

    @DeleteMapping("/account-movies/{accountId}")
    public void deleteAccountMovieByAccountId(@PathVariable int accountId) {
        accountMovieService.deleteByAccId(accountId);
    }
}
