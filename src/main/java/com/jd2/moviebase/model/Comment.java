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
public class Comment {
    private int id;
    private int accountId;
    private int movieId;
    private String note;
    private Date createdAt;
    private Date updatedAt;
    private Boolean isActive;
}
