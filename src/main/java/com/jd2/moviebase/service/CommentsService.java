package com.jd2.moviebase.service;

import com.jd2.moviebase.repository.CommentsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommentsService {
    private static final Logger logger = LoggerFactory.getLogger(CommentsService.class);
    private final CommentsRepository commentsRepository;

    public CommentsService(CommentsRepository commentsRepository) {
        this.commentsRepository = commentsRepository;
    }

    public void deactivateByAccId(int id) {
        logger.info("Deleting comment by account id: {}", id);
        commentsRepository.deactivateByAccId(id);
    }
}
