package com.example.demoboard.service;

import com.example.demoboard.domain.Comment;
import com.example.demoboard.domain.dto.CommentDto;
import com.example.demoboard.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    public List<CommentDto> findDtoByPostIdFetchWriter(Long postId) {
        return commentRepository.findAllByPostIdFetchWriterDto(postId);
    }

    @Transactional
    public void deleteById(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    public boolean isWrittenBy(Long commentId, Long writerId) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        return commentOptional.isPresent()
                && commentOptional.get().isWrittenBy(writerId);
    }
}
