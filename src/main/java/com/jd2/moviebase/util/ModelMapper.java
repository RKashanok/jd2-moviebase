package com.jd2.moviebase.util;

import com.jd2.moviebase.dto.AccountDto;
import com.jd2.moviebase.dto.CommentDto;
import com.jd2.moviebase.model.Account;

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
}
