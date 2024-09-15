package com.jd2.moviebase.service;

import com.jd2.moviebase.model.User;
import com.jd2.moviebase.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final DataSource ds;
    private final UserRepository userRepository;
    private final AccountService accountService;
    private final CommentsService commentsService;
    private final AccountMovieService accountMovieService;

    @Autowired
    public UserService(DataSource ds, UserRepository userRepository, AccountService accountService, CommentsService commentsService, AccountMovieService accountMovieService) {
        this.ds = ds;
        this.userRepository = userRepository;
        this.accountService = accountService;
        this.commentsService = commentsService;
        this.accountMovieService = accountMovieService;
    }

    public User create(User user) {
        logger.info("Creating user: {}", user);
        return userRepository.create(user);
    }

    public User findById(int id) {
        logger.info("Finding user by id: {}", id);
        return userRepository.findById(id);
    }

    public List<User> findAll() {
        logger.info("Finding all users");
        return userRepository.findAll();
    }

    public User update(User user) {
        logger.info("Updating user: {}", user);
        return userRepository.update(user);
    }

    public void deleteById(int id) {
        logger.info("Deleting user by id: {}", id);

        // get account id
        int accId = accountService.findByUserId(id).getId();

        // deactivate comments and set null account id
        commentsService.deactivateByAccId(accId);

        // delete user_movie
        accountMovieService.deleteByAccId(accId);

        //delete account
        accountService.deleteById(accId);

        // delete user
        userRepository.deleteById(id);
    }
}
