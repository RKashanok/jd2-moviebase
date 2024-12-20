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

    private static final String FIND_ALL_HQL = "FROM User";
    private static final String FIND_BY_EMAIL_HQL = "FROM User WHERE email = :email";

    private final SessionFactory sessionFactory;

    @Autowired
    public UserRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public List<User> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(FIND_ALL_HQL, User.class).getResultList();
        }
    }

    public Optional<User> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.get(User.class, id);
            return Optional.ofNullable(user);
        }
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

    public Optional<User> findByUserEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.createQuery(FIND_BY_EMAIL_HQL, User.class)
                    .setParameter("email", email)
                    .uniqueResult();
            return Optional.ofNullable(user);
        }
    }
}
