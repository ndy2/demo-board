package com.example.demoboard.security.provider;

import com.example.demoboard.security.service.AccountContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {


    @Autowired private UserDetailsService userDetailsService;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        AccountContext ac = (AccountContext)userDetailsService.loadUserByUsername(username);
        log.info("password : " + password);
        log.info("ac.getAccount().getPassword()"+ ac.getAccount().getPassword());
        System.out.println("ac.getAccount() = " + ac.getAccount());
        if(!passwordEncoder.matches(password,ac.getAccount().getPassword())){
            log.info("BadCredentialsException");
            throw new BadCredentialsException("BadCredentialsException");
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(ac.getAccount(),null,ac.getAuthorities());
        log.info("auth 성공");
        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
