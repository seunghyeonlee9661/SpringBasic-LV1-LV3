package com.example.SpringBoard.repository.backoffice;

import com.example.SpringBoard.entity.backoffice.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
