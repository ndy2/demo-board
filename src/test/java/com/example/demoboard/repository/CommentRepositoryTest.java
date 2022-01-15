package com.example.demoboard.repository;

import com.example.demoboard.domain.Comment;
import com.example.demoboard.domain.dto.CommentDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnitUtil;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class CommentRepositoryTest {

    @Autowired EntityManagerFactory emf;
    @Autowired CommentRepository commentRepository;

    @Test
    void writerFetchTest(){
        PersistenceUnitUtil util = emf.getPersistenceUnitUtil();
        List<Comment> comments = commentRepository.findAllByPostIdFetchWriter(29L);
        for (Comment comment : comments) {
            assertThat(util.isLoaded(comment.getWriter())).isTrue();

        }
    }

    @Test
    void writerFetchDtoTest(){
        List<CommentDto> commentDtos = commentRepository.findAllByPostIdFetchWriterDto(29L);
        for (CommentDto commentDto : commentDtos) {
            System.out.println("commentDto = " + commentDto);
        }
    }

    @Test
    @DisplayName("댓글이 없는 글은 empty list를 가져온다!")
    void fetchPostWithNoComments(){
        List<CommentDto> commentDtos = commentRepository.findAllByPostIdFetchWriterDto(25L);
        assertThat(commentDtos.isEmpty()).isTrue();
    }

}