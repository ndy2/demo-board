package com.example.demoboard.repository;

import com.example.demoboard.domain.Account;
import com.example.demoboard.domain.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.PersistenceUnitUtil;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountRepositoryTest {

    @Autowired AccountRepository accountRepository;
    @Autowired EntityManagerFactory emf;

    @Test
    void postListFetchTest(){
        PersistenceUnitUtil util = emf.getPersistenceUnitUtil();
        Account ndy = accountRepository.findByIdFetchPostList(1L);

        for (Post post : ndy.getPostList()) {
            assertThat(util.isLoaded(post)).isTrue();
        }
    }
}