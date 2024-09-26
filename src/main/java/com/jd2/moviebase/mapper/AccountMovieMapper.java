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

    public AccountMovie toModel(AccountMovieDTO accountMovieDTO) {
        AccountMovie accountMovie= new AccountMovie();
        accountMovie.setAccountId(accountMovieDTO.getAccountId());
        accountMovie.setMovieId(accountMovieDTO.getMovieId());
        accountMovie.setStatus(accountMovieDTO.getStatus());
        accountMovie.setCreatedAt(accountMovieDTO.getCreatedAt());
        accountMovie.setUpdatedAt(accountMovieDTO.getUpdatedAt());
        return accountMovie;
    }
}
