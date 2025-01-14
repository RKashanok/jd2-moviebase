package com.jd2.moviebase.repository;

import com.jd2.moviebase.model.AccountMovie;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountMovieRepository extends JpaRepository<AccountMovie, Long> {

    List<AccountMovie> findAllByAccountId(Long accountId);

    @Modifying
    @Query("UPDATE AccountMovie am SET am.status = :status WHERE am.account.id = :accountId AND am.movie.id = :movieId")
    int updateStatusByAccountId(Long accountId, Long movieId, String status);

    @Modifying
    @Query("DELETE FROM AccountMovie am WHERE am.account.id = :accountId")
    void deleteByAccountId(Long accountId);

    public List<AccountMovie> findAllByAccountId(Long accountId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(FIND_ALL_ACC_MOVIE_BY_ACC_ID_HQL, AccountMovie.class)
                    .setParameter("accountId", accountId)
                    .getResultList();
        }
    }

    @Transactional
    public AccountMovie create(AccountMovie accountMovie) {
        getCurrentSession().persist(accountMovie);
        return accountMovie;
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
