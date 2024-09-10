package com.jd2.moviebase.service;

import com.jd2.moviebase.config.DataSource;
import com.jd2.moviebase.model.User;
import com.jd2.moviebase.repository.AccountRepository;
import com.jd2.moviebase.repository.CommentsRepository;
import com.jd2.moviebase.repository.AccountMovieRepository;
import com.jd2.moviebase.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    DataSource ds = new DataSource();
    private final UserRepository userRepository;
    private final AccountService accountService;
    private final CommentsService commentsService;
    private final AccountMovieService accountMovieService;

    public UserService(UserRepository userRepository) throws ClassNotFoundException {
        this.userRepository = userRepository;
        this.accountService = new AccountService(new AccountRepository(ds));
        this.commentsService = new CommentsService(new CommentsRepository(ds));
        this.accountMovieService = new AccountMovieService(new AccountMovieRepository(ds));
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
