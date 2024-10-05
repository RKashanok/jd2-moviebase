package com.jd2.moviebase.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private Date createdAt;
    private Date updatedAt;
}
