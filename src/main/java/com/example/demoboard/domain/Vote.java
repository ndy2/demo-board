package com.example.demoboard.domain;

import com.example.demoboard.domain.util.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vote extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "vote_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "account_id", foreignKey = @ForeignKey(name="fk_vote_account"))
    private Account account;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id", foreignKey = @ForeignKey(name="fk_vote_post"))
    private Post post;

    @Enumerated(EnumType.STRING)
    private VoteType voteType;

    public static Vote createVote(VoteType voteType, Account account, Post post) {
        Vote vote = new Vote();
        vote.voteType = voteType;
        post.doVote(vote);
        account.vote(vote);
        return vote;
    }
}
