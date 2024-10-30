package com.jd2.moviebase.repository;

import com.jd2.moviebase.exception.MovieDbRepositoryOperationException;
import com.jd2.moviebase.model.AccountMovie;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.jd2.moviebase.util.ConstantsHelper.MovieStatus;

@Repository
public class AccountMovieRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public AccountMovieRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Transactional
    public List<AccountMovie> findAllByAccountId(Long accountId) {
        String hql = "FROM AccountMovie am WHERE am.account.id = :accountId";
        return getCurrentSession().createQuery(hql, AccountMovie.class)
                .setParameter("accountId", accountId)
                .getResultList();
    }

    public void create(AccountMovie accountMovie) {
        getCurrentSession().persist(accountMovie);
    }

    public void updateStatusByAccId(Long accountId, Long movieId, MovieStatus status) {
        String hql = "UPDATE AccountMovie am SET am.status = :status WHERE am.account.id = :accountId AND am.movie.id = :movieId";
        int updatedRows = getCurrentSession().createMutationQuery(hql)
                .setParameter("status", status.toString())
                .setParameter("accountId", accountId)
                .setParameter("movieId", movieId)
                .executeUpdate();

        if (updatedRows == 0) {
            throw new MovieDbRepositoryOperationException("Error updating account movies with Account ID"
                                                          + accountId + " and Movie ID " + movieId);
        }
    }

    public void deleteByAccId(Long id) {
        String hql = "DELETE FROM AccountMovie am WHERE am.account.id = :accountId";
        getCurrentSession().createMutationQuery(hql)
                .setParameter("accountId", id)
                .executeUpdate();
    }
}
