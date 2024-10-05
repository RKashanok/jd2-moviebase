package com.jd2.moviebase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private int id;
    private int accountId;
    private int movieId;
    private String note;
    private Boolean isActive;
}
