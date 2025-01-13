package com.jd2.moviebase.service;

import com.jd2.moviebase.dto.CommentDto;
import com.jd2.moviebase.exception.MovieDbRepositoryOperationException;
import com.jd2.moviebase.model.Comment;
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

    public CommentDto findById(Long id) {
        logger.info("Executing method: findById(id={})", id);
        return commentRepository.findById(id)
                .map(ModelMapper::toCommentDto)
                .orElseThrow(() -> new MovieDbRepositoryOperationException("Comment with ID " + id + " not found"));
    }

    public CommentDto create(CommentDto commentDto) {
        logger.info("Executing method: create(comment={})", commentDto);
        Comment comment = ModelMapper.toComment(commentDto);
        Comment createdComment = commentRepository.save(comment);
        return ModelMapper.toCommentDto(createdComment);
    }

    public CommentDto update(Long id, CommentDto commentDto) {
        logger.info("Executing method: update(comment={})", commentDto);
        commentDto.setId(id);
        Comment comment = ModelMapper.toComment(commentDto);
        if (!commentRepository.existsById(id)) {
            throw new MovieDbRepositoryOperationException("Comment with ID " + id + " not found");
        }
        Comment updatedComment = commentRepository.save(comment);
        return ModelMapper.toCommentDto(updatedComment);
    }

    public void deactivateByAccId(Long accountId) {
        logger.info("Executing method: deactivateByAccId(accountId={})", accountId);
        int updatedRows = commentRepository.deactivateByAccountId(accountId);
        if (updatedRows == 0) {
            throw new MovieDbRepositoryOperationException("No comments found for Account ID " + accountId);
        }
    }
}