package com.jd2.moviebase.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class RegisterUserDto {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String preferredName;
    private LocalDate dateOfBirth;
}
