package com.example.demoboard.domain;

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
    private LocalDateTime createDate;
    private String title;
    private String contents;
    private List<CommentDto> commentDtos;

    public PostContentDto(Long postId, String writerName, Long writerId, LocalDateTime createDate, String title, String contents) {
        this.postId = postId;
        this.writerName = writerName;
        this.writerId = writerId;
        this.createDate = createDate;
        this.title = title;
        this.contents = contents;
    }

    public void setCommentDtos(List<CommentDto> commentDtos) {
        this.commentDtos = commentDtos;
    }
}
