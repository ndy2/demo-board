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
@ToString(exclude = {"writer","comments"})
public class Post {

    @Id @GeneratedValue
    @Column(name="post_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="account_id", foreignKey = @ForeignKey(name="fk_post_writer"))
    private Account writer;

    private String title;

    @Lob
    private String contents;
    private LocalDateTime createDate;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    //==생성 매서드==// Create
    public static Post createPost(String title, String contents) {
        Post post = new Post();
        post.setTitle(title);
        post.setContents(contents);
        post.setCreateDate(LocalDateTime.now());
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

    //==연관관계 편의 매서드==//
    public void writeComment(Comment comment, Account writer) {
        this.comments.add(comment);
        comment.setWriter(writer);
        comment.setPost(this);
    }
}
