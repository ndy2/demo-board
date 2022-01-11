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

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    public Long register(AccountDto accountDto) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);

        Account account = modelMapper.map(accountDto,Account.class);
        accountRepository.save(account);

        return account.getId();
    }

    @Transactional
    public void edit(Account account, AccountEditDto accountEditDto) {
        Account findAccount = accountRepository.findById(account.getId()).get();

        //== DB의 정보 수정 ==//
        updateAccount(accountEditDto, findAccount);

        //== 현재 접속 중인 account 정보 수정==//
        updateAccount(accountEditDto, account);
        return;
    }

    private void updateAccount(AccountEditDto accountEditDto, Account findAccount) {
        findAccount.setEmail(accountEditDto.getEmail());
        findAccount.setName(accountEditDto.getName());
        findAccount.setPassword(accountEditDto.getPassword());
    }

    public Account findById(Long id) {
        return accountRepository.findById(id).get();
    }
}
