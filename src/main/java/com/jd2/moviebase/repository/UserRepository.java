package com.jd2.moviebase.repository;

import com.jd2.moviebase.exception.MovieDbRepositoryOperationException;
import com.jd2.moviebase.model.User;
import java.util.List;
import java.util.Optional;


import jakarta.transaction.Transactional;
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

    @Transactional
    public User create(User user) {
        getCurrentSession().persist(user);
        return user;
    }

    @Transactional
    public User update(User user) {
        User existingUser = getCurrentSession().find(User.class, user.getId());
        if (existingUser == null) {
            throw new MovieDbRepositoryOperationException("User with ID " + user.getId() + " not found");
        }
//        user.setUpdatedAt(LocalDateTime.now(ZoneId.of("UTC")));
        getCurrentSession().merge(user);
        return user;
    }

    @Transactional
    public void deleteById(Long id) {
        User user = getCurrentSession().find(User.class, id);
        if (user != null) {
            getCurrentSession().remove(user);
        } else {
            throw new MovieDbRepositoryOperationException("User with ID " + id + " not found for deletion");
        }
    }
}
