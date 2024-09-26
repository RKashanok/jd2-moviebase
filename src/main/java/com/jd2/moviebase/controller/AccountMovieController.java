package com.jd2.moviebase.controller;

import com.jd2.moviebase.dto.AccountMovieDTO;
import com.jd2.moviebase.model.AccountMovie;
import com.jd2.moviebase.service.AccountMovieService;
import com.jd2.moviebase.util.ConstantsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class AccountMovieController {
    private AccountMovieService accountMovieService;

    @Autowired
    public AccountMovieController(AccountMovieService accountMovieService) {
        this.accountMovieService = accountMovieService;
    }

    @GetMapping("/account-movies/{accountId}")
    public List<AccountMovieDTO> findByAccountId(@PathVariable(value = "accountId") int accountId) {
        return accountMovieService.findAllByAccountId(accountId);
    }

    @PostMapping("/account-movies")
    public void create(@RequestBody AccountMovieDTO accountMovieDTO) {
        accountMovieService.create(accountMovieDTO);
    }

    // Обновление статуса account_movie по accountId и movieId
    @PutMapping("/account-movies/{accountId}/{movieId}")
    public void updateMovieStatus(@PathVariable(value = "accountId") int accountId, @PathVariable(value = "movieId") int movieId,
                                  @RequestBody ConstantsHelper.MovieStatus status) {
        accountMovieService.updateStatusByAccId(accountId, movieId, status);
    }

    @DeleteMapping("/account-movies/{accountId}")
    public void deleteAccountMovieByAccountId(@PathVariable int accountId) {
        accountMovieService.deleteByAccId(accountId);
    }



}
