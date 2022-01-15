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
    private List<CommentDto> commentDtos;

    public PostContentDto(Long postId, String writerName, Long writerId, LocalDateTime createdDate, String title, String contents) {
        this.postId = postId;
        this.writerName = writerName;
        this.writerId = writerId;
        this.createdDate = createdDate;
        this.title = title;
        this.contents = contents.replace("\r\n","<br>");
    }

    public void setCommentDtos(List<CommentDto> commentDtos) {
        this.commentDtos = commentDtos;
    }
}
