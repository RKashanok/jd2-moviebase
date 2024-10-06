package com.jd2.moviebase.model;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Account {

    private int id;
    private int userId;
    private String firstName;
    private String lastName;
    private String preferredName;
    private Date dateOfBirth;
    private String phone;
    private String gender;
    private String photoUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
