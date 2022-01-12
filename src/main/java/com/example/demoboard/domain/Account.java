package com.example.demoboard.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"postList"})
public class Account {

    @Id @GeneratedValue
    @Column(name = "account_id")
    private Long id;
    private String name;
    private String username;
    private String password;
    private String email;
    private String role;

    @OneToMany(mappedBy = "writer")
    private List<Post> postList = new ArrayList<>();

    //==생성 매서드==// Create
    public static Account createAccount(String name, String username, String email, String password) {
        Account account = new Account();
        account.setName(name);
        account.setUsername(username);
        account.setEmail(email);
        account.setPassword(password);
        return account;
    }

    public static Account anonymousAccount() {
        return createAccount("","anonymous","","");
    }

    //==수정 매서드==// Update
    public void update(String email, String name, String password) {
        this.setEmail(email);
        this.setName(name);
        this.setPassword(password);
    }
}
