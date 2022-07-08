package com.example.SpringReactBookstore.services;

import com.example.SpringReactBookstore.models.User;
import com.example.SpringReactBookstore.models.UserDTO;
import com.example.SpringReactBookstore.repositories.UserDAO;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class UserService {

    private final UserDAO userDAO;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    public UserService(UserDAO userDAO, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    public UUID addUser(UserDTO userDTO){

        User user = modelMapper.map(userDTO, User.class);

        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        user.setId(null);

        User createdUser = userDAO.saveAndFlush(user);

        return createdUser.getId();
    }

    public UserDTO getUserByEmail(String email){

        User user = userDAO.findByEmail(email);

        if(Objects.isNull(user)){
            throw new RuntimeException("user doesn't exist with the email: " + email);
        }

        return modelMapper.map(user, UserDTO.class);
    }
}
