package com.jd2.moviebase.repository;

import com.jd2.moviebase.model.Comment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Comment c SET c.isActive = false, c.account = null WHERE c.account.id = :accountId")
    int deactivateByAccountId(Long accountId);
}
