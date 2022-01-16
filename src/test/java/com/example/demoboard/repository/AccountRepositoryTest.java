package com.example.demoboard.repository;

import com.example.demoboard.domain.Account;
import com.example.demoboard.domain.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnitUtil;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AccountRepositoryTest {

    @Autowired AccountRepository accountRepository;
    @Autowired EntityManagerFactory emf;

    @Test
    void postListFetchTest(){

    }
}