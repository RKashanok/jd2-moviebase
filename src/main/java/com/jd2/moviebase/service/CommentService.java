package com.jd2.moviebase.service;

import com.jd2.moviebase.dto.CommentDto;
import com.jd2.moviebase.repository.CommentRepository;
import com.jd2.moviebase.util.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentsRepository) {
        this.commentRepository = commentsRepository;
    }

    public List<CommentDto> findAll() {
        logger.info("Executing method: findAll()");
        return commentRepository.findAll().stream().map(ModelMapper::toCommentDto).toList();
    }

    public CommentDto findById(int id) {
        logger.info("Executing method: findById(id={})", id);
        return ModelMapper.toCommentDto(commentRepository.findById(id));
    }

    public CommentDto create(CommentDto commentDto) {
        logger.info("Executing method: create(comment={})", commentDto);
        return ModelMapper.toCommentDto(commentRepository.create(commentDto));
    }

    public CommentDto update(int id, CommentDto commentDto) {
        logger.info("Executing method: update(comment={})", commentDto);
        return ModelMapper.toCommentDto(commentRepository.update(id, commentDto));
    }

    public void deactivateByAccId(int accountId) {
        logger.info("Executing method: deactivateByAccId(accountId={})", accountId);
        commentRepository.deactivateByAccId(accountId);
    }
}