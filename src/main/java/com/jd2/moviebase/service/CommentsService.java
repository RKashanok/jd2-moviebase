package com.jd2.moviebase.service;

import com.jd2.moviebase.model.Comment;
import com.jd2.moviebase.repository.CommentsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CommentsService {
    private static final Logger logger = LoggerFactory.getLogger(CommentsService.class);
    private final CommentsRepository commentsRepository;

    public CommentsService(CommentsRepository commentsRepository) {
        this.commentsRepository = commentsRepository;
    }

    public Comment create(Comment comment) {
        logger.info("Creating comment: {}", comment);
        return commentsRepository.create(comment);
    }

    public Comment findById(int id) {
        logger.info("Finding comment by id: {}", id);
        return commentsRepository.findById(id);
    }

    public List<Comment> findAllByAcctId(int id) {
        logger.info("Finding comment by account id: {}", id);
        return commentsRepository.findAllByAcctId(id);
    }

    public List<Comment> findAllByMovieId(int id) {
        logger.info("Finding comment by movie id: {}", id);
        return commentsRepository.findAllByMovieId(id);
    }

    public Comment update(Comment comment) {
        logger.info("Updating comment: {}", comment.getId());
        return commentsRepository.update(comment);
    }

    public void deactivateByAccId(int id) {
        logger.info("Deleting comment by account id: {}", id);
        commentsRepository.deactivateByAccId(id);
    }
}
