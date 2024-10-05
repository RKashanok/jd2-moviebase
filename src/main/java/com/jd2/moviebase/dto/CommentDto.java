package com.jd2.moviebase.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentDto {

    private int id;
    private int accountId;
    private int movieId;
    private String note;
    private Boolean isActive;
}
