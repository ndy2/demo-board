package com.example.demoboard.service;

import com.example.demoboard.domain.Comment;
import com.example.demoboard.domain.CommentDto;
import com.example.demoboard.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    public List<CommentDto> findDtoByPostIdFetchWriter(Long postId) {
        return commentRepository.findAllByPostIdFetchWriterDto(postId);
    }

    public void deleteById(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
