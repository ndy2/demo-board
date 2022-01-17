package com.example.demoboard.controller.user;

import com.example.demoboard.domain.Account;
import com.example.demoboard.domain.dto.AccountDto;
import com.example.demoboard.domain.dto.AccountEditDto;
import com.example.demoboard.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.example.demoboard.domain.Account.createAccount;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final AccountService accountService;

    @ModelAttribute
    private Account getAccount() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal.equals("anonymousUser")){
            return Account.anonymousAccount();
        }
        return (Account) principal;
    }


    /**
     * 회원가입
     */
    @GetMapping("/form")
    public String signInForm(Model model){
        model.addAttribute("accountDto",new AccountDto());
        return "user/form";
    }

    @PostMapping("/form")
    public String signIn(@Validated AccountDto accountDto, BindingResult result){

        if(result.hasErrors()){
            for (ObjectError allError : result.getAllErrors()) {
                System.out.println("allError = " + allError);
            }
            return "user/form";
        }

        accountDto.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        Account account = createAccount(accountDto.getName(), accountDto.getUsername(), accountDto.getEmail(), accountDto.getPassword());
        accountService.register(account);
        return "redirect:/";
    }

    /**
     * 개인정보수정
     */
    @GetMapping("/edit")
    public String editUserForm(){
        return "user/edit";
    }

    @PostMapping("/edit")
    public String editUser(@Validated AccountEditDto accountEditDto, BindingResult result){
        //==SecurityContextHolder 에서 account 가져오기==//
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = (Account)authentication.getPrincipal();

        if(result.hasErrors()){
            accountService.edit(account,accountEditDto);
            return "/user/edit";
        }

        //accountService 를 이용해 DB 정보 변경 ==//
        accountEditDto.setPassword(passwordEncoder.encode(accountEditDto.getPassword()));
        accountService.edit(account,accountEditDto);

        //==update 된 Account 로 Authentication 만들고 SecurityContextHolder 에 보관==//
        Authentication updatedAuthentication =
                new UsernamePasswordAuthenticationToken(account, null, authentication.getAuthorities());
        SecurityContextHolder.setContext(new SecurityContextImpl(updatedAuthentication));
        return "redirect:/";
    }
}
