package com.employee.web.security;

import com.employee.web.controller.EmployeeController;
import com.employee.web.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomUserDetailsService.class);
    private User user;

    public CustomUserDetailsService() {
        super();
    }

    @PostConstruct
    public void completeSetup() {
        user = new User();
        user.setUsername("joe");
        BCryptPasswordEncoder encoder = passwordEncoder();
        user.setPassword(encoder.encode("mama"));
        LOGGER.info("Loaded user with credentials " + user.getUsername() + user.getPassword());
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return user;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}