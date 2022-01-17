package com.example.demoboard.repository;

import com.example.demoboard.domain.Account;
import com.example.demoboard.domain.Post;
import com.example.demoboard.domain.Vote;
import com.example.demoboard.domain.VoteType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote,Long> {
    @Query("select v from Vote v join v.post p join v.account a where v.voteType=:voteType and v.post=:post and v.account=:account")
    Optional<Vote> findByVoteTypeAndPostAndAccount(@Param("voteType") VoteType voteType,@Param("post") Post post,@Param("account") Account account);
}
