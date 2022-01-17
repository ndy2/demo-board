package com.example.demoboard.controller.post;

import com.example.demoboard.domain.Account;
import com.example.demoboard.domain.Post;
import com.example.demoboard.domain.dto.PostContentDto;
import com.example.demoboard.domain.dto.PostDto;
import com.example.demoboard.service.AccountService;
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
@RequestMapping("/qna")
@RequiredArgsConstructor
public class PostController {

    private final AccountService accountService;
    private final PostService postService;
    private final CommentService commentService;

    //== Account 정보 가져오기 ==//
    @ModelAttribute
    private Account getAccount() {
        return (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @GetMapping("/form")
    public String PostForm(){
        return "qna/form";
    }

    @PostMapping("/form")
    public String Post(@Validated PostDto postDto){
        Post post = createPost(postDto.getTitle(), postDto.getContents());
        getAccount().writePost(post);

        Long postId = postService.post(post);
        //게시글 작성후 게시글로 이동
        return "redirect:/qna/"+postId;
    }

    @GetMapping("/{postId}")
    public String PostContent(@PathVariable Long postId, Model model){

        model.addAttribute("post", postService.findContentDtoByIdFetchWriter(postId));
        model.addAttribute("comments",commentService.findDtoByPostIdFetchWriter(postId));

        return "qna/show";
    }

    @GetMapping("/{postId}/edit")
    public String PostEditForm(@PathVariable Long postId, Model model){
        //사용자 검증
        if(!isValidRequest(postId)){
            return "denied";
        }

        PostContentDto postDto = postService.findContentDtoByIdFetchWriter(postId);
        postDto.convertNewLine();
        model.addAttribute("post", postDto);
        return "qna/edit";
    }

    @PutMapping("/{postId}/edit")
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

    @DeleteMapping("/{postId}")
    public String PostDelete(@PathVariable Long postId){
        //사용자 검증
        if(!isValidRequest(postId)){
            return "denied";
        }
        //삭제
        getAccount().deletePost(postId);
        postService.delete(postId);

        //삭제 후 메인페이지로 이동
        return "redirect:/";
    }

    private boolean isValidRequest(Long postId) {
        return postService.isWrittenBy(postId, getAccount().getId());
    }
}
