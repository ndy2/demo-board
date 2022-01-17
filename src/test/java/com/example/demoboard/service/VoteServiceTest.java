package com.example.demoboard.service;

import com.example.demoboard.domain.Account;
import com.example.demoboard.domain.Post;
import com.example.demoboard.domain.VoteType;
import com.example.demoboard.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class VoteServiceTest {

    @Autowired PostService postService;
    @Autowired VoteService voteService;
    @Autowired AccountRepository accountRepository;


    @Test
    void duplicatedVoteTest(){

        Post post = postService.findById(29L);
        Account account = accountRepository.findById(1L).get();

        boolean votedBefore = voteService.isVotedBefore(VoteType.UP, post, account);
        assertThat(votedBefore).isTrue();
    }

}