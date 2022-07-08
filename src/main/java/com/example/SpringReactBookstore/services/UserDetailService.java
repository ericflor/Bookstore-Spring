package com.example.SpringReactBookstore.services;

import com.example.SpringReactBookstore.models.UserDTO;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailService implements UserDetailsService {

    private final UserService userService;

    public UserDetailService(UserService userService) {
        this.userService = userService;
    }

    // this is a function used by spring security for loading user from DB
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserDTO userByEmail = userService.getUserByEmail(email);

        return new User(userByEmail.getEmail(), userByEmail.getPassword(), new ArrayList<>());
    }
}
