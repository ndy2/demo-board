package com.example.demoboard;

import com.example.demoboard.domain.Account;
import com.example.demoboard.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;

import static com.example.demoboard.domain.Account.createAccount;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{

        private final EntityManager em;

        public void dbInit1(){
            Account account = createAccount("득윤", "ndy2", "emrdbs12@gmail.com", "{noop}1234");
            em.persist(account);

            for (int i = 0; i < 13; i++) {
                Post post = createPostOf(account,i);
                em.persist(post);
            }
        }

        public void dbInit2(){
            Account account = createAccount("하영", "bhy2", "bhy@gmail.com", "{noop}1234");
            em.persist(account);

            for (int i = 0; i < 13; i++) {
                Post post = createPostOf(account,i);
                em.persist(post);
            }
        }
    }

    private static Post createPostOf(Account account,int i) {
        Post post = new Post();
        post.setTitle("나는 " + account.getName() + i);
        post.setWriter(account);
        post.setContents("나는 내용 나는 내용 나는 내용 나는 내용 나는 내용 나는 내용");
        post.setDateTime(LocalDateTime.now());
        return post;
    }




}