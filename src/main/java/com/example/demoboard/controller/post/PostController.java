package com.example.demoboard.controller.post;

import com.example.demoboard.domain.Account;
import com.example.demoboard.domain.PostContentDto;
import com.example.demoboard.domain.PostUploadDto;
import com.example.demoboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("qna/form")
    public String PostForm(Model model){
        Account account = getAccount();
        model.addAttribute("writer", account.getName());

        return "qna/form";
    }

    @PostMapping("qna/form")
    public String Post(@Validated PostUploadDto postUploadDto){
        Account account = getAccount();
        Long postId = postService.post(account.getId(), postUploadDto);

        //게시글 작성후 게시글 페이지로 이동
        return "redirect:/qna/show/"+postId;
    }

    @GetMapping("qna/show/{postId}")
    public String PostContent(@PathVariable Long postId, Model model){
        PostContentDto postContentDto = postService.findPostContentDtoById(postId);
        model.addAttribute("post",postContentDto);

        return "qna/show";
    }


    //== Account 정보 가져오기 ==//
    private Account getAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (Account) authentication.getPrincipal();
    }

}
