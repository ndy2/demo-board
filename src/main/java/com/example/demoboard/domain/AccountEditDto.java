package com.example.demoboard.domain;

import lombok.Data;

@Data
public class AccountEditDto {
    private String password;
    private String name;
    private String email;
}
