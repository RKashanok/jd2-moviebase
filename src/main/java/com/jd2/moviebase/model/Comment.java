package com.jd2.moviebase.model;

import java.util.Date;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Comment {

    private int id;
    private int accountId;
    private int movieId;
    private String note;
    private Date createdAt;
    private Date updatedAt;
    private Boolean isActive;
}
