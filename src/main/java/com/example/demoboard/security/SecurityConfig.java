package com.example.demoboard.security;

import com.example.demoboard.security.handler.CustomAccessDeniedHandler;
import com.example.demoboard.security.provider.CustomAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private final AuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final AuthenticationFailureHandler customAuthenticationFailureHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        return new CustomAuthenticationProvider();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        CustomAccessDeniedHandler accessDeniedHandler = new CustomAccessDeniedHandler();
        accessDeniedHandler.setErrorPage("/denied");
        return accessDeniedHandler;
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/fonts/**","/css/**","/js/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers()
                .xssProtection()
                .and()
                .contentSecurityPolicy("script-src 'self'");

        http
                .authorizeRequests()
                .antMatchers("/*","/login*","/error","/user/form","/h2-console*","/h2-console/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(customAuthenticationSuccessHandler)
                .failureHandler(customAuthenticationFailureHandler)
                .permitAll();
        http
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler());

        http
                .csrf().requireCsrfProtectionMatcher(new AntPathRequestMatcher("!/h2-console/**"))
                .and().headers().addHeaderWriter(new StaticHeadersWriter("X-Content-Security-Policy","script-src 'self'"))
                .frameOptions().disable();


    }
}
