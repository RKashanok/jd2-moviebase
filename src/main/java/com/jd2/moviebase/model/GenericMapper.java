package com.jd2.moviebase.model;

import com.jd2.moviebase.dto.AccountMovieDTO;

public class GenericMapper {
    public static AccountMovieDTO accountMovieToDto(AccountMovie accountMovie) {
        AccountMovieDTO accountMovieDTO = new AccountMovieDTO();
        accountMovieDTO.setAccountId(accountMovie.getAccountId());
        accountMovieDTO.setMovieId(accountMovie.getMovieId());
        accountMovieDTO.setStatus(accountMovie.getStatus());
        accountMovieDTO.setCreatedAt(accountMovie.getCreatedAt());
        accountMovieDTO.setUpdatedAt(accountMovie.getUpdatedAt());
        return accountMovieDTO;
    }

    public static AccountMovie accountMovieDtoToModel(AccountMovieDTO accountMovieDTO) {
        AccountMovie accountMovie= new AccountMovie();
        accountMovie.setAccountId(accountMovieDTO.getAccountId());
        accountMovie.setMovieId(accountMovieDTO.getMovieId());
        accountMovie.setStatus(accountMovieDTO.getStatus());
        accountMovie.setCreatedAt(accountMovieDTO.getCreatedAt());
        accountMovie.setUpdatedAt(accountMovieDTO.getUpdatedAt());
        return accountMovie;
    }
}
