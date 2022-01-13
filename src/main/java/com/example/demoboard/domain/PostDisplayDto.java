package com.example.demoboard.domain;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Index 페이지에 뿌리기 위한 Post Dto (내용 제외)
 */
@Data
public class PostDisplayDto {
    private Long id;
    private String writerName;
    private String title;
    private LocalDateTime dateTime;

    public PostDisplayDto(Long id, String writerName, String title, LocalDateTime dateTime) {
        this.id = id;
        this.writerName = writerName;
        this.title = title;
        this.dateTime = dateTime;
    }

    public PostDisplayDto(Post post){
        this.id = post.getId();
        this.writerName = post.getWriter().getName();
        this.title = post.getTitle();
        this.dateTime = post.getDateTime();
    }
}
