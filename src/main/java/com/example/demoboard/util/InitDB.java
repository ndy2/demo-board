package com.example.demoboard.util;

import com.example.demoboard.domain.Account;
import com.example.demoboard.domain.Comment;
import com.example.demoboard.domain.Post;
import com.example.demoboard.service.AccountService;
import com.example.demoboard.service.CommentService;
import com.example.demoboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

import static com.example.demoboard.domain.Account.createAccount;
import static com.example.demoboard.domain.Comment.createComment;
import static com.example.demoboard.domain.Post.createPost;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    @PostConstruct
    public void init(){
        Account ndy = initService.dbInit1("득윤");
        Account bhy = initService.dbInit2("하영");
        initService.dbInit3(ndy,bhy);
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{

        public static final String content = "내용 내용 내용 내용 내용 내용 내용 내용 내용 내용";
//        private final EntityManager em;
        private final AccountService accountService;
        private final PostService postService;
        private final CommentService commentService;

        public Account dbInit1(String name){

            Account ndy = createAccount(name, "ndy2", "emrdbs12@gmail.com", "{noop}1234");
            accountService.register(ndy);

            for (int i = 0; i < 13; i++) {
                Post post = createPost("나는 " + name + i, content);
                ndy.writePost(post);

                postService.post(post);
            }
            return ndy;
        }
        public Account dbInit2(String name){
            Account bhy = createAccount(name, "bhy2", "bhy@gmail.com", "{noop}1234");
            accountService.register(bhy);

            for (int i = 0; i < 13; i++) {
                Post post = createPost("나는 " + name + i, content);
                bhy.writePost(post);
                postService.post(post);
            }
            return bhy;
        }

        public void dbInit3(Account ndy, Account bhy){
            Post post = createPost("나는 득윤득윤득윤" , content);
            ndy.writePost(post);
            postService.post(post);
            Comment comment1 = createComment("1빠");
            bhy.writeCommentOnPost(comment1,post);

            Comment comment2 = createComment("2빠");
            ndy.writeCommentOnPost(comment2,post);

            commentService.save(comment1);
            commentService.save(comment2);

        }
    }

}