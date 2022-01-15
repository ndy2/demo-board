package com.example.demoboard.domain;

import com.example.demoboard.domain.util.BaseEntity;
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
@ToString(exclude = {"writer","post"})
public class Comment extends BaseEntity {
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


    //==생성 매서드 ==//
    public static Comment createComment(String contents){
        Comment comment = new Comment();
        comment.contents=contents;
        return comment;
    }

    public void setWriter(Account writer) {
        this.writer=writer;
    }
    public void setPost(Post post) {
        this.post = post;
    }

    //==비즈니스 로직==//
    public boolean isWrittenBy(Long writerId) {
        return writer.getId()==writerId;
    }
}
