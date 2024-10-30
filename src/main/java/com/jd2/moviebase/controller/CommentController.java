package com.jd2.moviebase.controller;

import com.jd2.moviebase.dto.CommentDto;
import com.jd2.moviebase.service.CommentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping()
    public List<CommentDto> findAll() {
        return commentService.findAll();
    }

    @GetMapping("/{id}")
    public CommentDto findById(@PathVariable("id") Long id) {
        return commentService.findById(id);
    }

    @PostMapping
    public CommentDto create(@RequestBody CommentDto commentDto) {
        return commentService.create(commentDto);
    }

    @PutMapping("/{id}")
    public CommentDto update(@PathVariable("id") Long id, @RequestBody CommentDto commentDto) {
        return commentService.update(id, commentDto);
    }

    @DeleteMapping("/{id}")
    public void deactivateByAccId(@PathVariable("id") Long id) {
        commentService.deactivateByAccId(id);
    }
}
