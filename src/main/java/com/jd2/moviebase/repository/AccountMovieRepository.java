package com.jd2.moviebase.repository;

import com.jd2.moviebase.exception.MovieDbRepositoryOperationException;
import com.jd2.moviebase.model.AccountMovie;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.jd2.moviebase.util.ConstantsHelper.MovieStatus;

@Repository
public interface AccountMovieRepository extends JpaRepository<AccountMovie, Long> {

    List<AccountMovie> findAllByAccountId(Long accountId);

    @Modifying
    @Transactional
    @Query("UPDATE AccountMovie am SET am.status = :status WHERE am.account.id = :accountId AND am.movie.id = :movieId")
    int updateStatusByAccountId(Long accountId, Long movieId, String status);

    @Modifying
    @Transactional
    @Query("DELETE FROM AccountMovie am WHERE am.account.id = :accountId")
    void deleteByAccountId(Long accountId);
}
