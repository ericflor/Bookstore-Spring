package com.example.SpringReactBookstore.services;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    public UserDetailService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    // this is function used by spring security for loading user from DB
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new User("TheBob123@gmail.com", passwordEncoder.encode("1pass3word3"), new ArrayList<>());
    }
}
