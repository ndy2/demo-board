package com.example.demoboard.controller.user;

import com.example.demoboard.domain.AccountDto;
import com.example.demoboard.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final AccountService accountService;

    @GetMapping("/user/form")
    public String signInForm(Model model){
        model.addAttribute("memberForm",new AccountDto());
        return "user/form";
    }

    @PostMapping("/user/form")
    public String signIn(AccountDto accountDto){

        accountDto.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        accountService.register(accountDto);
        return "redirect:/";
    }
}
