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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = (Account) authentication.getPrincipal();

        model.addAttribute("writer", account.getName());

        return "qna/form";
    }

    @PostMapping("qna/form")
    public String Post(@Validated PostUploadDto postUploadDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = (Account) authentication.getPrincipal();

        Long postId = postService.post(account.getId(), postUploadDto);

        return "redirect:/qna/show/"+postId;
    }

    @GetMapping("qna/show/{postId}")
    public String PostContent(@PathVariable Long postId, Model model){

        System.out.println("postId = " + postId);
        System.out.println("PostController.PostContent");

        PostContentDto dto = postService.findPostContentDtoById(postId);
        model.addAttribute("post",dto);

        return "qna/show";
    }

}
