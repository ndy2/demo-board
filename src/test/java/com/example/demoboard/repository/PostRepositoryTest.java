package com.example.demoboard.repository;

import com.example.demoboard.domain.Post;
import com.example.demoboard.domain.dto.PostContentDto;
import com.example.demoboard.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnitUtil;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class PostRepositoryTest {

    @Autowired PostRepository postRepository;
    @Autowired PostService postService;

    @Autowired EntityManagerFactory emf;


    @Test
    @DisplayName("findByIdFetchWriter 를 사용하면 post 의 writer는 loaded 되어야 한다.")
    void Post2CommentFetchTest(){
        PersistenceUnitUtil util = emf.getPersistenceUnitUtil();
        Post post = postRepository.findByIdFetchWriter(29L);
        assertThat(util.isLoaded(post.getWriter())).isTrue();
    }

    @Test
    @DisplayName("findByIdFetchWriterDto 를 사용하면 postDto 의 writerName 을 정상적으로 끌어온다.")
    void commentFetchDtoTest(){
        PostContentDto postContentDto = postRepository.findByIdFetchWriterDto(29L);
        System.out.println("postContentDto = " + postContentDto);
    }

}