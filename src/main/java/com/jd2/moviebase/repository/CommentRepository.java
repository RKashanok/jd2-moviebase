package com.jd2.moviebase.repository;

import com.jd2.moviebase.exception.MovieDbRepositoryOperationException;
import com.jd2.moviebase.model.Comment;

import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CommentRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public CommentRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public List<Comment> findAll() {
        return getCurrentSession().createQuery("FROM Comment", Comment.class).getResultList();
    }

    public Comment findById(Long id) {
        Comment comment = getCurrentSession().find(Comment.class, id);
        return Optional.ofNullable(comment)
            .orElseThrow(() -> new MovieDbRepositoryOperationException("Comment with ID " + id + " not found"));
    }

    @Transactional
    public Comment create(Comment comment) {
        getCurrentSession().persist(comment);
        return comment;
    }

    @Transactional
    public Comment update(Comment comment) {
        Comment existingComment = getCurrentSession().find(Comment.class, comment.getId());
        if (existingComment == null) {
            throw new MovieDbRepositoryOperationException("Comment with ID " + comment.getId() + " not found");
        }
        getCurrentSession().merge(comment);
        return comment;
    }

    @Transactional
    public void deactivateByAccId(Long id) {
        String hql = "UPDATE Comment c SET c.isActive = false, c.account = null WHERE c.account.id = :accountId";
        int updatedRows = getCurrentSession().createMutationQuery(hql)
                .setParameter("accountId", id)
                .executeUpdate();

        if (updatedRows == 0) {
            throw new MovieDbRepositoryOperationException("No comments found for Account ID " + id);
        }
    }
}
