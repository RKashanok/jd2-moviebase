package com.jd2.moviebase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private int id;
    private int userId;
    private String firstName;
    private String lastName;
    private String preferredName;
    private Date dateOfBirth;
    private String phone;
    private String gender;
    private String photoUrl;
}
