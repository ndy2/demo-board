package com.example.demoboard.controller.user;

import com.example.demoboard.domain.Account;
import com.example.demoboard.domain.AccountDto;
import com.example.demoboard.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

//@RestController
//@RequiredArgsConstructor
public class UserApiController {

    private /*final*/ AccountService accountService;

    @GetMapping("/api/users/{id}")
    public AccountDto show(@PathVariable Long id){
        Account account = accountService.findById(id);
        return new AccountDto(account.getName(), account.getUsername(), account.getPassword(), account.getEmail());
    }
}
