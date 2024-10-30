package com.jd2.moviebase.repository;

import com.jd2.moviebase.exception.MovieDbRepositoryOperationException;
import com.jd2.moviebase.model.Movie;
import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MovieRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public MovieRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public List<Movie> findAll() {
        return getCurrentSession().createQuery("FROM Movie", Movie.class).getResultList();
    }

    public Optional<Movie> findById(Long id) {
        Movie movie = getCurrentSession().find(Movie.class, id);
        return Optional.ofNullable(movie);
    }

    @Transactional
    public Movie create(Movie movie) {
        getCurrentSession().persist(movie);
        return movie;
    }

    @Transactional
    public Movie update(Movie movie) {
        Movie existingMovie = getCurrentSession().get(Movie.class, movie.getId());
        if (existingMovie == null) {
            throw new MovieDbRepositoryOperationException("Movie with ID " + movie.getId() + " not found");
        }
        getCurrentSession().merge(movie);
        return movie;
    }

    @Transactional
    public void deleteById(Long id) {
        Movie movie = getCurrentSession().find(Movie.class, id);
        if (movie != null) {
            getCurrentSession().remove(movie);
        } else {
            throw new MovieDbRepositoryOperationException("Movie with ID " + id + " not found for deletion");
        }
    }
}
