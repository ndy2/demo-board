package com.example.demoboard.controller.post;

import com.example.demoboard.domain.Account;
import com.example.demoboard.service.CommentService;
import com.example.demoboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/qna")
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;
    private final CommentService commentService;

    //== Account 정보 가져오기 ==//
    private Account getAccount() {
        return (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }



}
