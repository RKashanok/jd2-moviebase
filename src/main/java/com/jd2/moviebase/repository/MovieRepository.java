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

    private static final String FIND_ALL_HQL = "FROM Movie";
    private static final String FIND_BY_TMDB_ID_HQL = "FROM Movie m WHERE m.tmdbId = :tmdbId";

    private final SessionFactory sessionFactory;

    @Autowired
    public MovieRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public List<Movie> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(FIND_ALL_HQL, Movie.class).getResultList();
        }
    }

    public Optional<Movie> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Movie movie = session.find(Movie.class, id);
            return Optional.ofNullable(movie);
        }
    }

    public Optional<Movie> findByTmdbId(Long tmdbId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(FIND_BY_TMDB_ID_HQL, Movie.class)
                    .setParameter("tmdbId", tmdbId)
                    .uniqueResultOptional();
        }
    }

    @Transactional
    public Movie create(Movie movie) {
        getCurrentSession().persist(movie);
        return movie;
    }

    @Transactional
    public Movie createIfNotExist(Movie movie) {
        Optional<Movie> existingMovie = findByTmdbId(movie.getTmdbId());
        return existingMovie.orElseGet(() -> create(movie));
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
