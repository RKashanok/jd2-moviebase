package com.jd2.moviebase.mapper;

import com.jd2.moviebase.dto.AccountMovieDTO;
import com.jd2.moviebase.model.AccountMovie;

public class AccountMovieMapper {
    public AccountMovieDTO toDto(AccountMovie accountMovie) {
        AccountMovieDTO accountMovieDTO = new AccountMovieDTO();
        accountMovieDTO.setAccountId(accountMovie.getAccountId());
        accountMovieDTO.setMovieId(accountMovie.getMovieId());
        accountMovieDTO.setStatus(accountMovie.getStatus());
        accountMovieDTO.setCreatedAt(accountMovie.getCreatedAt());
        accountMovieDTO.setUpdatedAt(accountMovie.getUpdatedAt());
        return accountMovieDTO;
    }

    public AccountMovieDTO toModel(AccountMovie accountMovie) {
        AccountMovieDTO accountMovieDTO = new AccountMovieDTO();
        accountMovieDTO.setAccountId(accountMovie.getAccountId());
        accountMovieDTO.setMovieId(accountMovie.getMovieId());
        accountMovieDTO.setStatus(accountMovie.getStatus());
        accountMovieDTO.setCreatedAt(accountMovie.getCreatedAt());
        accountMovieDTO.setUpdatedAt(accountMovie.getUpdatedAt());
        return accountMovieDTO;
    }
}
