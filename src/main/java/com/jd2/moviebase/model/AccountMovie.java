package com.jd2.moviebase.model;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountMovie {

    private int accountId;
    private int movieId;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
