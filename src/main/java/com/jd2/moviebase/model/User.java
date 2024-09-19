package com.jd2.moviebase.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class User {
    private int id;
    private String email;
    private String password;
    private String role;
    private Date createdAt;
    private Date updatedAt;
}
