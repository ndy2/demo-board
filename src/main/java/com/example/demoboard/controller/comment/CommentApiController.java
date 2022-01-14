package com.example.demoboard.controller.comment;


import com.example.demoboard.domain.Account;
import com.example.demoboard.domain.Comment;
import com.example.demoboard.domain.CommentDto;
import com.example.demoboard.domain.Post;
import com.example.demoboard.service.CommentService;
import com.example.demoboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentApiController {

    //== Account 정보 가져오기 ==//
    @ModelAttribute
    private Account getAccount() {
        return (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private final PostService postService;
    private final CommentService commentService;

    @PostMapping("/api/qna/{postId}/comment")
    public CommentDto comment(@PathVariable Long postId, String contents){
        Account writer = getAccount();
        Post post = postService.findById(postId);
        Comment comment = Comment.createComment(contents);
        writer.writeCommentOnPost(comment,post);
        commentService.save(comment);

        return new CommentDto(writer.getId(), writer.getName(),comment.getContents(),comment.getCreateDate(),comment.getId());
    }

    @DeleteMapping("/api/qna/{postId}/comment/{commentId}")
    public String delete(@PathVariable Long commentId){
        commentService.deleteById(commentId);
        return "ok";
    }
}
