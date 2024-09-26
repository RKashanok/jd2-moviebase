package com.jd2.moviebase.model;

import lombok.Data;

import java.util.Date;
@Data
public class AccountMovie {
    private int accountId;
    private int movieId;
    private String status;
    private Date createdAt;
    private Date updatedAt;

}
