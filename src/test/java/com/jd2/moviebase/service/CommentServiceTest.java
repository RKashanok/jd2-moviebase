package com.jd2.moviebase.service;

import com.jd2.moviebase.dto.CommentDto;
import com.jd2.moviebase.exception.MovieDbRepositoryOperationException;
import com.jd2.moviebase.model.Account;
import com.jd2.moviebase.model.Comment;
import com.jd2.moviebase.model.Movie;
import com.jd2.moviebase.repository.CommentRepository;
import com.jd2.moviebase.util.ModelMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    @Test
    void findAll_ShouldReturnListOfCommentDtos() {
        when(commentRepository.findAll()).thenReturn(List.of(getComment()));

        List<CommentDto> result = commentService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(getCommentDto().getId(), result.get(0).getId());
        verify(commentRepository, times(1)).findAll();
    }

    @Test
    void findById_ShouldReturnCommentDto_WhenCommentExists() {
        when(commentRepository.findById(1L)).thenReturn(getComment());

        CommentDto result = commentService.findById(1L);

        assertNotNull(result);
        assertEquals(getCommentDto().getId(), result.getId());
        verify(commentRepository, times(1)).findById(1L);
    }

    @Test
    void findById_ShouldThrowException_WhenCommentDoesNotExist() {
        when(commentRepository.findById(1L)).thenThrow(new MovieDbRepositoryOperationException("Comment with ID 1 not found"));

        assertThrows(MovieDbRepositoryOperationException.class, () -> commentService.findById(1L));
        verify(commentRepository, times(1)).findById(1L);
    }

    @Test
    void create_ShouldReturnCreatedCommentDto() {
        Comment comment = getComment();
        CommentDto commentDto = getCommentDto();
        when(commentRepository.create(any(Comment.class))).thenReturn(comment);

        CommentDto result = commentService.create(commentDto);

        assertNotNull(result);
        assertEquals(commentDto.getId(), result.getId());
        verify(commentRepository, times(1)).create(comment);
    }

    @Test
    void update_ShouldReturnUpdatedCommentDto() {
        Comment comment = getComment();
        CommentDto commentDto = getCommentDto();
        when(commentRepository.update(any(Comment.class))).thenReturn(comment);

        CommentDto result = commentService.update(1L, commentDto);

        assertNotNull(result);
        assertEquals(commentDto.getId(), result.getId());
        verify(commentRepository, times(1)).update(comment);
    }

    @Test
    void deactivateByAccId_ShouldInvokeRepositoryDeactivateByAccId() {
        doNothing().when(commentRepository).deactivateByAccId(1L);

        commentService.deactivateByAccId(1L);

        verify(commentRepository, times(1)).deactivateByAccId(1L);
    }

    @Test
    void deactivateByAccId_ShouldThrowException_WhenNoCommentsFound() {
        doThrow(new MovieDbRepositoryOperationException("No comments found for Account ID 1")).when(commentRepository).deactivateByAccId(1L);

        assertThrows(MovieDbRepositoryOperationException.class, () -> commentService.deactivateByAccId(1L));
        verify(commentRepository, times(1)).deactivateByAccId(1L);
    }

    private Comment getComment() {
        return Comment.builder()
                .id(1L)
                .account(new Account())
                .movie(new Movie())
                .note("Great movie!")
                .isActive(true)
                .build();
    }

    private CommentDto getCommentDto() {
        return CommentDto.builder()
                .id(1L)
                .note("Great movie!")
                .isActive(true)
                .build();
    }
}