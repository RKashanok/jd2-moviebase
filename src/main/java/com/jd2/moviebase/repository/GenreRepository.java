package com.jd2.moviebase.repository;

import com.jd2.moviebase.exception.MovieDbRepositoryOperationException;
import com.jd2.moviebase.model.Genre;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class GenreRepository {

    private static final String FIND_ALL_HQL = "FROM Genre";

    private final SessionFactory sessionFactory;

    @Autowired
    public GenreRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public List<Genre> findAll() {
        try (Session session = sessionFactory.openSession()) {
        return session.createQuery(FIND_ALL_HQL, Genre.class).getResultList();
        }
    }

    public Optional<Genre> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Genre genre = session.get(Genre.class, id);
            return Optional.ofNullable(genre);
        }
    }

    @Transactional
    public Genre create(Genre genre) {
        getCurrentSession().persist(genre);
        return genre;
    }

    @Transactional
    public Genre update(Genre genre) {
        Genre existingGenre = getCurrentSession().find(Genre.class, genre.getId());
        if (existingGenre == null) {
            throw new MovieDbRepositoryOperationException("Genre with ID " + genre.getId() + " not found");
        }
        getCurrentSession().merge(genre);
        return genre;
    }

    @Transactional
    public void deleteById(Long id) {
        Session session = getCurrentSession();
        Genre genre = session.get(Genre.class, id);
        if (genre != null) {
            session.remove(genre);
        } else {
            throw new MovieDbRepositoryOperationException("Genre with ID " + id + " not found for deletion");
        }
    }
}
