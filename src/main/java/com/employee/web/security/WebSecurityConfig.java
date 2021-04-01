package com.employee.web.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                    .and()
                .authorizeRequests()
                    .antMatchers(HttpMethod.GET).permitAll()
                    .and()
                .authorizeRequests()
                    .antMatchers(HttpMethod.PUT).permitAll()
                    .and()
                .authorizeRequests()
                    .antMatchers(HttpMethod.POST).permitAll()
                    .and()
                .authorizeRequests()
                    .antMatchers(HttpMethod.DELETE).authenticated()
                .anyRequest().authenticated();
        http.csrf().disable();
    }
}
