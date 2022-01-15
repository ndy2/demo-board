package com.example.demoboard.controller.post;

import com.example.demoboard.domain.Account;
import com.example.demoboard.domain.Post;
import com.example.demoboard.domain.PostContentDto;
import com.example.demoboard.domain.PostDto;
import com.example.demoboard.service.CommentService;
import com.example.demoboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.example.demoboard.domain.Post.createPost;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final CommentService commentService;

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
        Account writer = getAccount();
        Post post = createPost(postDto.getTitle(), postDto.getContents());
        writer.writePost(post);

        Long postId = postService.post(post);

        //게시글 작성후 게시글로 이동
        return "redirect:/qna/"+postId;
    }

    @GetMapping("qna/{postId}")
    public String PostContent(@PathVariable Long postId, Model model){

        model.addAttribute("post", postService.findContentDtoByIdFetchWriter(postId));
        model.addAttribute("comments",commentService.findDtoByPostIdFetchWriter(postId));

        return "qna/show";
    }

    @GetMapping("qna/edit/{postId}")
    public String PostEditForm(@PathVariable Long postId, Model model){
        //사용자 검증
        if(!isValidRequest(postId)){
            return "denied";
        }

        PostContentDto postDto = postService.findContentDtoByIdFetchWriter(postId);
        model.addAttribute("post", postDto);
        return "qna/edit";
    }

    @PutMapping("qna/edit/{postId}")
    public String PostEdit(@PathVariable Long postId, PostDto postDto){
        //사용자 검증
        if(!isValidRequest(postId)){
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
        if(!isValidRequest(postId)){
            return "denied";
        }
        //수정
        postService.delete(postId);

        //삭제 후 메인페이지로 이동
        return "redirect:/";
    }

    private boolean isValidRequest(Long postId) {
        return postService.isWrittenBy(postId, getAccount().getId());
    }
}
