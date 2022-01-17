package com.example.demoboard.domain.dto;

import com.example.demoboard.domain.Post;
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
    private LocalDateTime createdDate;

    public PostDisplayDto(Long id, String writerName, String title, LocalDateTime createdDate) {
        this.id = id;
        this.writerName = writerName;
        this.title = title;
        this.createdDate = createdDate;
    }

    public PostDisplayDto(Post post){
        this.id = post.getId();
        this.writerName = post.getWriter().getName();
        this.title = post.getDisplayTitle();
        this.createdDate = post.getCreatedDate();
    }
}
