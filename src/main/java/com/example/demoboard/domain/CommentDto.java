package com.example.demoboard.domain;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class CommentDto {
    private Long writerId;
    private String writerName;
    private String contents;
    private LocalDateTime createDate;
    private Long id;

    public CommentDto(Long writerId, String writerName, String contents, LocalDateTime createDate, Long id) {
        this.writerId = writerId;
        this.writerName = writerName;
        this.contents = contents;
        this.createDate = createDate;
        this.id = id;
    }
}
