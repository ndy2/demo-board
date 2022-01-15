package com.example.demoboard.domain.dto;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class CommentDto {
    private Long writerId;
    private String writerName;
    private String contents;
    private LocalDateTime createdDate;
    private Long id;

    public CommentDto(Long writerId, String writerName, String contents, LocalDateTime createdDate, Long id) {
        this.writerId = writerId;
        this.writerName = writerName;
        this.contents = contents.replace("\r\n","<br>");
        this.createdDate = createdDate;
        this.id = id;
    }
}
