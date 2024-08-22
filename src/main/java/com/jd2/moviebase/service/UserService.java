package com.jd2.moviebase.service;

import com.jd2.moviebase.config.DataSource;
import com.jd2.moviebase.model.User;
import com.jd2.moviebase.repository.AccountRepository;
import com.jd2.moviebase.repository.CommentsRepository;
import com.jd2.moviebase.repository.UserMovieRepository;
import com.jd2.moviebase.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(User user) {
        logger.info("Creating user: {}", user);
        return userRepository.create(user);
    }

    public User findById(int id) {
        logger.info("Finding user by id: {}", id);
        return userRepository.findById(id);
    }

    public User update(User user) {
        logger.info("Updating user: {}", user);
        return userRepository.update(user);
    }

    public void deleteById(int id) {
        logger.info("Deleting user by id: {}", id);
        DataSource ds = new DataSource();

        // get account id
        AccountService accountService = new AccountService(new AccountRepository(ds));
        int accId = accountService.findByUserId(id).getId();

        // deactivate comments
        CommentsService commentsService = new CommentsService(new CommentsRepository(ds));
        commentsService.deactivateByAccId(accId);

        // delete user_movie
        UserMovieService userMovieService = new UserMovieService(new UserMovieRepository(ds));
        userMovieService.deleteByAccId(accId);

        //delete account
        accountService.deleteById(accId);

        // delete user
        userRepository.deleteById(id);
    }
}
