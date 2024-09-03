package com.jd2.moviebase.service;

import com.jd2.moviebase.model.Comment;
import com.jd2.moviebase.repository.CommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentsRepository) {
        this.commentRepository = commentsRepository;
    }

    public List<Comment> findAll() {
        logger.info("Executing method: findAll()");
        return commentRepository.findAll();
    }

    public Optional<Comment> findById(int id) {
        logger.info("Executing method: findById(id={})", id);
        return commentRepository.findById(id);
    }

    public Comment create(Comment comment) {
        logger.info("Executing method: create(comment={})", comment);
        return commentRepository.create(comment);
    }

    public Comment update(Comment comment) {
        logger.info("Executing method: update(comment={})", comment);
        return commentRepository.update(comment);
    }

    public void deactivateByAccId(int accountId) {
        logger.info("Executing method: deactivateByAccId(accountId={})", accountId);
        commentRepository.deactivateByAccId(accountId);
    }
}