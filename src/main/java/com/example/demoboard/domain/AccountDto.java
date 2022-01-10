package com.example.demoboard.domain;

import lombok.Data;

@Data
public class AccountDto {

    private String name;
    private String username;
    private String password;
    private String email;
    private String role;
}
