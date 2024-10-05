package com.jd2.moviebase.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private int id;
    private int accountId;
    private int movieId;
    private String note;
    private Date createdAt;
    private Date updatedAt;
    private Boolean isActive;
}
