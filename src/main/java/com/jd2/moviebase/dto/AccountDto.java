package com.jd2.moviebase.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private String firstName;
    private String lastName;
    private String preferredName;
    private Date dateOfBirth;
    private String phone;
    private String gender;
    private String photoUrl;
}
