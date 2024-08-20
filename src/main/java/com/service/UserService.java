package com.service;

import com.moviebase.beans.User;
import com.repository.UserRepository;
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
        userRepository.deleteById(id);
    }
}
