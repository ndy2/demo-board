package com.example.demoboard.domain;

import com.example.demoboard.domain.util.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"writer","comments"})
public class Post extends BaseEntity {

    @Id @GeneratedValue
    @Column(name="post_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="account_id", foreignKey = @ForeignKey(name="fk_post_writer"))
    private Account writer;

    private String title;

    @Lob
    private String contents;

    @OneToMany(mappedBy = "post", cascade = REMOVE)
    private List<Comment> comments = new ArrayList<>();

    private int commentsNumber;
    private int voteUpCount;

    //==생성 매서드==// Create
    public static Post createPost(String title, String contents) {
        Post post = new Post();
        post.setTitle(title);
        post.setContents(contents);
        return post;
    }

    //==수정 매서드==// Update
    public void edit(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    //==연관관계 편의 매서드==//
    public void writeComment(Comment comment, Account writer) {
        this.comments.add(comment);
        commentsNumber +=1;
        comment.setWriter(writer);
        comment.setPost(this);
    }

    public void deleteComment(Long commentId) {
        comments.removeIf(c->c.getId()==commentId);
        commentsNumber -=1;
    }

    public void doVote(Vote vote) {
        vote.setPost(this);
        voteUpCount+=1;
    }

    //==비즈니스 로직==//
    public boolean isWrittenBy(Long writerId) {
        return writer.getId() == writerId;
    }

    public String getDisplayTitle(){
        if(commentsNumber==0){
            return title;
        }else{
            return title + " ["+commentsNumber+"]";
        }
    }
}
