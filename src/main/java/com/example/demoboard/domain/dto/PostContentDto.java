package com.example.demoboard.domain.dto;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString(exclude = "commentDtos")
public class PostContentDto {
    private Long postId;
    private String writerName;
    private Long writerId;
    private LocalDateTime createdDate;
    private String title;
    private String contents;
    private int voteUpCount;

    private List<CommentDto> commentDtos;

    public PostContentDto(Long postId, String writerName, Long writerId, LocalDateTime createdDate, String title, String contents, int voteUpCount) {
        this.postId = postId;
        this.writerName = writerName;
        this.writerId = writerId;
        this.createdDate = createdDate;
        this.title = title;
        this.voteUpCount = voteUpCount;
        this.contents = contents.replace("\r\n","<br>");
    }

    public void setCommentDtos(List<CommentDto> commentDtos) {
        this.commentDtos = commentDtos;
    }
}
