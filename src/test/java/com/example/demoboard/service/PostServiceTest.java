package com.example.demoboard.service;

import com.example.demoboard.domain.*;
import com.example.demoboard.domain.dto.PostDisplayDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.demoboard.domain.Account.createAccount;
import static com.example.demoboard.domain.Post.createPost;

@SpringBootTest
@Transactional
@Rollback(value = false)
class PostServiceTest {

    @Autowired AccountService accountService;
    @Autowired PostService postService;

    @Test
    void pagingTest(){
        //given
        Account ndy = createAccount("득윤", "ndyy2", "emrdbs12@gmail.com", "{noop}1234");
        Account bhy = createAccount("하영", "bhyy2", "bhy@gmail.com", "{noop}1234");
        accountService.register(ndy);
        accountService.register(bhy);

        for (int i = 0; i < 13; i++) {
            Post post = createPost("나는 득윤", "나는 내용 나는 내용");
            ndy.writePost(post);
            postService.post(post);
            System.out.println("post = " + post);
        }

        for (int i = 0; i < 15; i++) {
            Post post = createPost("나는 하영", "나는 내용 나는 내용");
            bhy.writePost(post);
            postService.post(post);
            System.out.println("post = " + post);
        }

        //when
        PageRequest pageRequest = PageRequest.of(1,10);
        Page<PostDisplayDto> result = postService.findPostDisplayDto(pageRequest);

        //then
        List<PostDisplayDto> postDisplayDtos = result.getContent();
        for (PostDisplayDto dto : postDisplayDtos) {
            System.out.println("dto = " + dto);
        }

        long count = result.getTotalElements();
        int numPages = result.getTotalPages();
        System.out.println("count = " + count + " numPages = " + numPages);

    }
}