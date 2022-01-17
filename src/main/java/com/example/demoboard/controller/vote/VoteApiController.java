package com.example.demoboard.controller.vote;

import com.example.demoboard.domain.Account;
import com.example.demoboard.domain.Post;
import com.example.demoboard.domain.Vote;
import com.example.demoboard.domain.VoteType;
import com.example.demoboard.domain.util.Result;
import com.example.demoboard.service.PostService;
import com.example.demoboard.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/qna/{postId}/vote")
@RequiredArgsConstructor
public class VoteApiController {

    private final PostService postService;
    private final VoteService voteService;

    //== Account 정보 가져오기 ==//
    private Account getAccount() {
        return (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @PostMapping("/up")
    public Result recommend(@PathVariable Long postId){

        Post post = postService.findById(postId);
        Account account = getAccount();

        //추천 중복 검사
        boolean votedBefore = voteService.isVotedBefore(VoteType.UP,post,account);
        if(votedBefore){
            return Result.fail("이미 추천 하였습니다!");
        }

        Vote vote = Vote.createVote(VoteType.UP, account, post);
        voteService.save(vote);

        return Result.ok();
    }

}
