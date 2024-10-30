package com.jd2.moviebase.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Account {

    private Long id;
    private Long userId;
    private String firstName;
    private String lastName;
    private String preferredName;
    private LocalDate dateOfBirth;
    private String phone;
    private String gender;
    private String photoUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
