package com.example.demoboard.controller.post;

import com.example.demoboard.domain.Account;
import com.example.demoboard.domain.Post;
import com.example.demoboard.domain.PostDto;
import com.example.demoboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    //== Account 정보 가져오기 ==//
    @ModelAttribute
    private Account getAccount() {
        return (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @GetMapping("qna/form")
    public String PostForm(){
        return "qna/form";
    }

    @PostMapping("qna/form")
    public String Post(@Validated PostDto postDto){
        Account account = getAccount();
        Long postId = postService.post(account.getId(), postDto);

        //게시글 작성후 게시글로 이동
        return "redirect:/qna/"+postId;
    }

    @GetMapping("qna/{postId}")
    public String PostContent(@PathVariable Long postId, Model model){
        Post post = postService.findById(postId);
        model.addAttribute("post",post);
        return "qna/show";
    }

    @GetMapping("qna/edit/{postId}")
    public String PostEditForm(@PathVariable Long postId, Model model){
        //사용자 검증
        if(!isValidEditRequest(postId)){
            return "denied";
        }

        Post post = postService.findById(postId);
        model.addAttribute("post",post);
        return "qna/edit";
    }

    @PutMapping("qna/edit/{postId}")
    public String PostEdit(@PathVariable Long postId, PostDto postDto){
        //사용자 검증
        if(!isValidEditRequest(postId)){
            return "denied";
        }
        //수정
        postService.edit(postId,postDto);

        //수정 후 수정된 게시글로 이동
        return "redirect:/qna/"+postId;
    }

    @DeleteMapping("qna/{postId}")
    public String PostDelete(@PathVariable Long postId){
        //사용자 검증
        if(!isValidEditRequest(postId)){
            return "denied";
        }
        //수정
        postService.delete(postId);

        //삭제 후 메인페이지로 이동
        return "redirect:/";
    }

    private boolean isValidEditRequest(Long postId) {
        return postService.isWrittenBy(postId, getAccount().getId());
    }
}
