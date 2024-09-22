package com.jd2.moviebase.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class AccountMovie {
    private int accountId;
    private int movieId;
    private String status;
    private Date createdAt;
    private Date updatedAt;
}
