package com.jd2.moviebase.dto;

import lombok.Data;

import java.util.Date;
@Data
public class AccountMovieDTO {
    private int accountId;
    private int movieId;
    private String status;
    private Date createdAt;
    private Date updatedAt;
}
