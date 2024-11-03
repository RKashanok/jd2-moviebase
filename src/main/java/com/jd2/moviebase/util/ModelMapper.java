package com.jd2.moviebase.util;

import com.jd2.moviebase.dto.*;
import com.jd2.moviebase.model.*;

public class ModelMapper {

    public static AccountDto toAccountDto(Account account) {
        return AccountDto.builder()
                .id(account.getId())
                .userId(account.getUser() != null ? account.getUser().getId() : null)
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .preferredName(account.getPreferredName())
                .dateOfBirth(account.getDateOfBirth())
                .phone(account.getPhone())
                .gender(account.getGender())
                .photoUrl(account.getPhotoUrl())
                .build();
    }

    public static Account toAccount(AccountDto accountDto) {
        return Account.builder()
                .id(accountDto.getId())
//                .userId(accountDto.getUserId())
                .firstName(accountDto.getFirstName())
                .lastName(accountDto.getLastName())
                .preferredName(accountDto.getPreferredName())
                .dateOfBirth(accountDto.getDateOfBirth())
                .phone(accountDto.getPhone())
                .gender(accountDto.getGender())
                .photoUrl(accountDto.getPhotoUrl())
                .build();
    }

    public static CommentDto toCommentDto(com.jd2.moviebase.model.Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .accountId(comment.getAccount() != null ? comment.getAccount().getId() : null)
                .movieId(comment.getMovie() != null ? comment.getMovie().getId() : null)
                .note(comment.getNote())
                .isActive(comment.getIsActive())
                .build();
    }

    public static Comment toComment(CommentDto commentDto) {
        return Comment.builder()
                .id(commentDto.getId())
//                .accountId(commentDto.getAccountId())
//                .movieId(commentDto.getMovieId())
                .note(commentDto.getNote())
                .isActive(commentDto.getIsActive())
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

    public static MovieDto toMovieDto(Movie movie) {
        return MovieDto.builder()
                .id(movie.getId())
                .tmdbId(movie.getTmdbId())
                .name(movie.getName())
                .genreId(movie.getGenres() != null ? movie.getGenres().stream().map(Genre::getId).toList() : null)
                .releaseDate(movie.getReleaseDate())
                .rating(movie.getRating())
                .overview(movie.getOverview())
                .originalLanguage(movie.getOriginalLanguage())
                .build();
    }

    public static Movie toMovie(MovieDto movieDto) {
        return Movie.builder()
                .id(movieDto.getId())
                .tmdbId(movieDto.getTmdbId())
                .name(movieDto.getName())
//                .genreId(movieDto.getGenreId())
                .releaseDate(movieDto.getReleaseDate())
                .rating(movieDto.getRating())
                .overview(movieDto.getOverview())
                .originalLanguage(movieDto.getOriginalLanguage())
                .build();
    }

        public static AccountMovieDto toAccountMovieDto(AccountMovie accountMovie) {
            return AccountMovieDto.builder()
                    .accountId(accountMovie.getAccount() != null ? accountMovie.getAccount().getId() : null)
                    .movieId(accountMovie.getMovie() != null ? accountMovie.getMovie().getId() : null)
                    .status(accountMovie.getStatus())
                    .createdAt(accountMovie.getCreatedAt())
                    .updatedAt(accountMovie.getUpdatedAt())
                    .build();
        }

        public static AccountMovie toAccountMovie(AccountMovieDto accountMovieDto) {
            return AccountMovie.builder()
//                    .accountId(accountMovieDto.getAccountId())
//                    .movieId(accountMovieDto.getMovieId())
                    .status(accountMovieDto.getStatus())
                    .createdAt(accountMovieDto.getCreatedAt())
                    .updatedAt(accountMovieDto.getUpdatedAt())
                    .build();
        }
}
