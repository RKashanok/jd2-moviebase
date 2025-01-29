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
import java.util.Optional;

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
        when(commentRepository.findById(1L)).thenReturn(Optional.ofNullable(getComment()));

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
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        CommentDto result = commentService.create(commentDto);

        assertNotNull(result);
        assertEquals(commentDto.getId(), result.getId());
        verify(commentRepository, times(1)).save(comment);
    }

    @Test
    void update_ShouldReturnUpdatedCommentDto() {
        Comment comment = getComment();
        CommentDto commentDto = getCommentDto();
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        when(commentRepository.existsById(anyLong())).thenReturn(true);

        CommentDto result = commentService.update(1L, commentDto);

        assertNotNull(result);
        assertEquals(commentDto.getId(), result.getId());
        verify(commentRepository, times(1)).save(comment);
    }

//    @Test
//    void deactivateByAccId_ShouldInvokeRepositoryDeactivateByAccId() {
//        when(commentRepository.deactivateByAccountId(1L)).thenReturn(1);;
//
//        commentService.deactivateByAccId(1L);
//
//        verify(commentRepository, times(1)).deactivateByAccountId(1L);
//    }

//    @Test
//    void deactivateByAccId_ShouldThrowException_WhenNoCommentsFound() {
//        doThrow(new MovieDbRepositoryOperationException("No comments found for Account ID 1")).when(commentRepository).deactivateByAccountId(1L);
//
//        assertThrows(MovieDbRepositoryOperationException.class, () -> commentService.deactivateByAccId(1L));
//        verify(commentRepository, times(1)).deactivateByAccountId(1L);
//    }

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