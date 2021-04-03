package com.employee.web.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Set authentication rules for Spring Security
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic() // required for basic header authentication
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
                    .antMatchers(HttpMethod.DELETE).authenticated() //  only delete requests require authentication
                .anyRequest().authenticated();
        http.csrf().disable(); // disable CSRF so we can test PUT, POST, and DELETE commands with curl and postman. Should re-enable if adding front end with csrf token.
    }
}
