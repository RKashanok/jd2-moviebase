package com.jd2.moviebase.dto;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountDto {
    private Long id;
    private Long userId;
    private String firstName;
    private String lastName;
    private String preferredName;
    private LocalDate dateOfBirth;
    private String phone;
    private String gender;
    private String photoUrl;
}
