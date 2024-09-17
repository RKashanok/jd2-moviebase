package com.jd2.moviebase.service;

import com.jd2.moviebase.repository.CommentsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentsService {
    private static final Logger logger = LoggerFactory.getLogger(CommentsService.class);
    private final CommentsRepository commentsRepository;

    @Autowired
    public CommentsService(CommentsRepository commentsRepository) {
        this.commentsRepository = commentsRepository;
    }

    public void deactivateByAccId(int id) {
        logger.info("Deleting comment by account id: {}", id);
        commentsRepository.deactivateByAccId(id);
    }
}
