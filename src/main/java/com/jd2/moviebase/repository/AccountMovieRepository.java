package com.jd2.moviebase.repository;

import com.jd2.moviebase.exception.MovieDbRepositoryOperationException;
import com.jd2.moviebase.model.AccountMovie;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

import static com.jd2.moviebase.util.ConstantsHelper.MovieStatus;

@Repository
public class AccountMovieRepository {

    private static final String FIND_ALL_ACC_MOVIE_BY_ACC_ID_HQL = "FROM AccountMovie am WHERE am.account.id = :accountId";
    private static final String UPDATE_ACC_MOVIE_STATUS_BY_ACC_ID_HQL = "UPDATE AccountMovie am SET am.status = :status WHERE am.account.id = :accountId AND am.movie.id = :movieId";
    private static final String DELETE_ACC_MOVIE_BY_ACC_ID_HQL = "DELETE FROM AccountMovie am WHERE am.account.id = :accountId";

    private final SessionFactory sessionFactory;

    @Autowired
    public AccountMovieRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public List<AccountMovie> findAllByAccountId(Long accountId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(FIND_ALL_ACC_MOVIE_BY_ACC_ID_HQL, AccountMovie.class)
                    .setParameter("accountId", accountId)
                    .getResultList();
        }
    }

    @Transactional
    public Long create(AccountMovie accountMovie) {
        getCurrentSession().persist(accountMovie);
        return accountMovie.getId();
    }

    @Transactional
    public void updateStatusByAccId(Long accountId, Long movieId, MovieStatus status) {
        int updatedRows = getCurrentSession().createMutationQuery(UPDATE_ACC_MOVIE_STATUS_BY_ACC_ID_HQL)
                .setParameter("status", status.toString())
                .setParameter("accountId", accountId)
                .setParameter("movieId", movieId)
                .executeUpdate();

        if (updatedRows == 0) {
            throw new MovieDbRepositoryOperationException("Error updating account movies with Account ID"
                    + accountId + " and Movie ID " + movieId);
        }
    }

    @Transactional
    public void deleteByAccId(Long accountId) {
        getCurrentSession().createMutationQuery(DELETE_ACC_MOVIE_BY_ACC_ID_HQL)
                .setParameter("accountId", accountId)
                .executeUpdate();
    }
}
