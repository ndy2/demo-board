package com.example.demoboard.controller.comment;


import com.example.demoboard.domain.*;
import com.example.demoboard.domain.dto.CommentDto;
import com.example.demoboard.service.CommentService;
import com.example.demoboard.service.PostService;
import com.example.demoboard.domain.util.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentApiController {

    //== Account 정보 가져오기 ==//
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

        return new CommentDto(writer.getId(), writer.getName(),comment.getContents(),comment.getCreatedDate(),comment.getId());
    }

    @DeleteMapping("/api/qna/{postId}/comment/{commentId}")
    public Result delete(@PathVariable Long commentId){

        if(!isValidRequest(commentId)){
            return Result.fail("권한이 없습니다.");
        }

        commentService.deleteById(commentId);
        return Result.ok();
    }

    private boolean isValidRequest(Long commentId) {
        return commentService.isWrittenBy(commentId,getAccount().getId());
    }
}
