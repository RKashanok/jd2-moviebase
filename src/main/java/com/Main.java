package com;

import com.movie.database.datasource.DataSource;
import com.moviebase.beans.User;
import com.repository.UserRepository;
import com.service.UserService;

public class Main {
    public static void main(String[] args) {
        DataSource ds = new DataSource();
        UserRepository ur = new UserRepository(ds);
        UserService us = new UserService(ur);
        User user = us.findById(1);
        System.out.println("User found: " + user.getEmail());
    }
}
