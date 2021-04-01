/*
package com.employee.web.security;

import com.employee.web.model.User;
import com.employee.web.services.JSONLoaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    private JSONLoaderService jsonLoaderService;

    @Autowired
    public void setJsonLoaderService(JSONLoaderService jsonLoaderService) {
        this.jsonLoaderService = jsonLoaderService;
    }

    public CustomUserDetailsService() {
        super();
    }

    @PostConstruct
    public void loadUser() {
        user = jsonLoaderService.getLoadedUser();
        BCryptPasswordEncoder encoder = passwordEncoder();
        user.setPassword(encoder.encode(user.getPassword())); // encrypt password
        LOGGER.info("Loaded user with credentials username: " + user.getUsername() + " password: " +  user.getPassword());
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
*/
