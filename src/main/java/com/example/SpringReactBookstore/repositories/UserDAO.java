package com.example.SpringReactBookstore.repositories;

import com.example.SpringReactBookstore.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserDAO extends JpaRepository<User, UUID> {

    User findByEmail(String email);
}
