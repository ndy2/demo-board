package com.example.demoboard.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@ToString(exclude = {"account","post"})
public class Comment {
    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="account_id",foreignKey = @ForeignKey(name="fk_comment_writer"))
    private Account writer;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="post_id", foreignKey = @ForeignKey(name="fk_comment_to_post"))
    private Post post;

    @Lob
    private String contents;

    private LocalDateTime createDate;

    //==생성 매서드 ==//
    public static Comment createComment(String contents){
        Comment comment = new Comment();
        comment.contents=contents;
        comment.createDate=LocalDateTime.now();
        return comment;
    }

    public void setWriter(Account writer) {
        this.writer=writer;
    }
}
