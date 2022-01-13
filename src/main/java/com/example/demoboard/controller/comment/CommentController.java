package com.example.demoboard.controller.comment;

import com.example.demoboard.domain.Account;
import com.example.demoboard.domain.Comment;
import com.example.demoboard.domain.Post;
import com.example.demoboard.service.CommentService;
import com.example.demoboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class CommentController {

    //== Account 정보 가져오기 ==//
    @ModelAttribute
    private Account getAccount() {
        return (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private final PostService postService;
    private final CommentService commentService;

    @PostMapping("/qna/{postId}/answers")
    public String comment(@PathVariable Long postId, String contents){
        Account writer = getAccount();
        Post post = postService.findById(postId);
        Comment comment = Comment.createComment(contents);
        writer.writeCommentOnPost(comment,post);

        commentService.save(comment);
        return "redirect:/qna/" + postId;
    }
}
