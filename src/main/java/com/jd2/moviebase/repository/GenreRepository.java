package com.jd2.moviebase.repository;

import com.jd2.moviebase.exception.MovieDbRepositoryOperationException;
import com.jd2.moviebase.model.Genre;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class GenreRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public GenreRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Transactional(readOnly = true)
    public List<Genre> findAll() {
        return getCurrentSession().createQuery("FROM Genre", Genre.class).getResultList();
    }

    @Transactional(readOnly = true)
    public Optional<Genre> findById(Long id) {
        Genre genre = getCurrentSession().get(Genre.class, id);
        return Optional.ofNullable(genre);
    }

    @Transactional
    public Genre create(Genre genre) {
        getCurrentSession().persist(genre);
        return genre;
    }

    @Transactional
    public Genre update(Genre genre) {
        Session session = getCurrentSession();
        Genre existingGenre = session.get(Genre.class, genre.getId());
        if (existingGenre == null) {
            throw new MovieDbRepositoryOperationException("Genre with ID " + genre.getId() + " not found");
        }
        return (Genre) session.merge(genre);
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
