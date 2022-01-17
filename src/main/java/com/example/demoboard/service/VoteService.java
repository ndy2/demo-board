package com.example.demoboard.service;

import com.example.demoboard.domain.Account;
import com.example.demoboard.domain.Post;
import com.example.demoboard.domain.Vote;

import com.example.demoboard.domain.VoteType;
import com.example.demoboard.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VoteService {

    private final VoteRepository voteRepository;

    @Transactional
    public void save(Vote vote) {
        voteRepository.save(vote);
    }

    public boolean isVotedBefore(VoteType voteType, Post post, Account account) {
        return voteRepository.findByVoteTypeAndPostAndAccount(voteType, post, account).isPresent();
    }
}
