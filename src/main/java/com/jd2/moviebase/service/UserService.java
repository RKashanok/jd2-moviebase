package com.jd2.moviebase.service;

import com.jd2.moviebase.exception.MovieDbRepositoryOperationException;
import com.jd2.moviebase.model.User;
import com.jd2.moviebase.model.UserDetailModel;
import com.jd2.moviebase.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final DataSource ds;
    private final UserRepository userRepository;
    private final AccountService accountService;
    private final CommentService commentService;
    private final AccountMovieService accountMovieService;
    private final PasswordEncoder passwordEncoder;

    public User create(User user) {
        logger.info("Creating user: {}", user);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    public User findById(Long id) {
        logger.info("Finding user by id: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new MovieDbRepositoryOperationException("User not found with id: " + id));
    }

    public List<User> findAll() {
        logger.info("Finding all users");
        return userRepository.findAll();
    }

    public User update(User user) {
        logger.info("Updating user: {}", user);
        if (!userRepository.existsById(user.getId())) {
            throw new MovieDbRepositoryOperationException("User with ID " + user.getId() + " not found");
        }
        return userRepository.save(user);
    }

    public void deleteById(Long id) {
        logger.info("Deleting user by id: {}", id);
        Long accId = accountService.findByUserId(id).getId();
        accountService.deleteById(accId);
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);

        return user.map(u -> new UserDetailModel(u, accountService.findByUserId(u.getId()).getId()))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
}
