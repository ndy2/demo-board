package com.example.demoboard.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostContentDto {

    private String writer;
    private String title;
    private String contents;
    private LocalDateTime dateTime;

    public PostContentDto(Post post){
        this.writer = post.getWriter().getName();
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.dateTime = post.getDateTime();
    }
}
