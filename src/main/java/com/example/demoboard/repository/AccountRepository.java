package com.example.demoboard.repository;

import com.example.demoboard.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByUsername(String username);

    @Query("select a from Account a join fetch a.postList where a.id=:id")
    Account findByIdFetchPostList(@Param("id") Long id);
}
