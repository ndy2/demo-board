package com.example.demoboard.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"writer"})
public class Post {

    @Id @GeneratedValue
    @Column(name="post_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="account_id")
    private Account writer;
    private String title;

    @Lob
    private String contents;
    private LocalDateTime dateTime;

    @OneToMany
    @JoinColumn(name="comment_id")
    private List<Comment> comments = new ArrayList<>();

    //==생성 매서드==// Create
    public static Post createPost(Account writer, String title, String contents) {
        Post post = new Post();
        post.setWriter(writer);
        post.setTitle(title);
        post.setContents(contents);
        post.setDateTime(LocalDateTime.now());
        return post;
    }

    public boolean isWrittenBy(Long writerId) {
        return writer.getId() == writerId;
    }

    //==수정 매서드==// Update
    public void edit(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
