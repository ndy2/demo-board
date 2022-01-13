package com.example.demoboard.service;

import com.example.demoboard.domain.Account;
import com.example.demoboard.domain.AccountDto;
import com.example.demoboard.domain.AccountEditDto;
import com.example.demoboard.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demoboard.domain.Account.createAccount;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    public Long register(Account account) {
        accountRepository.save(account);
        System.out.println("account = " + account);
        return account.getId();
    }

    @Transactional
    public void edit(Account account, AccountEditDto accountEditDto) {
        Account findAccount = accountRepository.findById(account.getId()).get();

        //== DB의 정보 수정 ==//
        findAccount.update(accountEditDto.getEmail(),accountEditDto.getName(), accountEditDto.getPassword());

        //== 현재 접속 중인 account 정보 수정==//
        account.update(accountEditDto.getEmail(),accountEditDto.getName(), accountEditDto.getPassword());
    }


    public Account findById(Long id) {
        return accountRepository.findById(id).get();
    }
}
