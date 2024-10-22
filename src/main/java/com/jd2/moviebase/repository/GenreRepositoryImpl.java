package com.jd2.moviebase.repository;

import com.jd2.moviebase.model.Genre;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class GenreRepositoryImpl implements GenreRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public GenreRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<Genre> findAll() {
        return getCurrentSession().createQuery("from Genre", Genre.class).list();
    }

    @Override
    public Optional<Genre> findById(Long id) {
        Genre genre = getCurrentSession().get(Genre.class, id);
        return Optional.ofNullable(genre);
    }

    @Override
    public Genre create(Genre genre) {
        getCurrentSession().save(genre);
        return genre;
    }

    @Override
    public Genre update(Genre genre) {
        getCurrentSession().update(genre);
        return genre;
    }

    @Override
    public void deleteById(Long id) {
        Genre genre = getCurrentSession().get(Genre.class, id);
        if (genre != null) {
            getCurrentSession().delete(genre);
        }
    }
}
