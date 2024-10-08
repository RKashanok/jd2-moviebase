package com.jd2.moviebase.dto;

import java.util.Date;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountDto {

    private Integer id;
    private Integer userId;
    private String firstName;
    private String lastName;
    private String preferredName;
    private Date dateOfBirth;
    private String phone;
    private String gender;
    private String photoUrl;
}
