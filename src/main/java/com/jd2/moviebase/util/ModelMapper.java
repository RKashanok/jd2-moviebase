package com.jd2.moviebase.util;

import com.jd2.moviebase.dto.AccountDto;
import com.jd2.moviebase.dto.CommentDto;
import com.jd2.moviebase.model.Account;
import com.jd2.moviebase.dto.GenreDto;
import com.jd2.moviebase.model.Genre;

public class ModelMapper {

    public static AccountDto toAccountDto(Account account) {
        return AccountDto.builder()
                .id(account.getId())
                .userId(account.getUserId())
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .preferredName(account.getPreferredName())
                .dateOfBirth(account.getDateOfBirth())
                .phone(account.getPhone())
                .gender(account.getGender())
                .photoUrl(account.getPhotoUrl())
                .build();
    }

    public static CommentDto toCommentDto(com.jd2.moviebase.model.Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .accountId(comment.getAccountId())
                .movieId(comment.getMovieId())
                .note(comment.getNote())
                .isActive(comment.getIsActive())
                .build();
    }


    public static Account toAccount(AccountDto accountDto) {
        return Account.builder()
            .id(accountDto.getId())
            .userId(accountDto.getUserId())
            .firstName(accountDto.getFirstName())
            .lastName(accountDto.getLastName())
            .preferredName(accountDto.getPreferredName())
            .dateOfBirth(accountDto.getDateOfBirth())
            .phone(accountDto.getPhone())
            .gender(accountDto.getGender())
            .photoUrl(accountDto.getPhotoUrl())
            .build();
    }

        public static GenreDto toGenreDto(Genre genre) {
            return GenreDto.builder()
                    .id(genre.getId())
                    .tmdbId(genre.getTmdbId())
                    .name(genre.getName())
                    .build();
        }

        public static Genre toGenre(GenreDto genreDTO) {
            return Genre.builder()
                    .id(genreDTO.getId())
                    .tmdbId(genreDTO.getTmdbId())
                    .name(genreDTO.getName())
                    .build();
        }
}
