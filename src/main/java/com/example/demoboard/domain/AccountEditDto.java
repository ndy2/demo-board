package com.example.demoboard.domain;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class AccountEditDto {

    @NotEmpty
    private String password;

    @NotEmpty
    private String name;

    @NotEmpty
    @Email
    private String email;
}
