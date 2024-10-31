package com.jd2.moviebase.repository;

import com.jd2.moviebase.exception.MovieDbRepositoryOperationException;
import com.jd2.moviebase.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public List<User> findAll() {
        return getCurrentSession().createQuery("FROM User", User.class).getResultList();
    }

    public Optional<User> findById(Long id) {
        User user = getCurrentSession().get(User.class, id);
        return Optional.ofNullable(user);
    }

    public User create(User user) {
        getCurrentSession().persist(user);
        return user;
    }

    public User update(User user) {
        User existingUser = getCurrentSession().find(User.class, user.getId());
        if (existingUser == null) {
            throw new MovieDbRepositoryOperationException("User with ID " + user.getId() + " not found");
        }
//        user.setUpdatedAt(LocalDateTime.now(ZoneId.of("UTC")));
        getCurrentSession().merge(user);
        return user;
    }

    public void deleteById(Long id) {
        User user = getCurrentSession().find(User.class, id);
        if (user != null) {
            getCurrentSession().remove(user);
        } else {
            throw new MovieDbRepositoryOperationException("User with ID " + id + " not found for deletion");
        }
    }
}
