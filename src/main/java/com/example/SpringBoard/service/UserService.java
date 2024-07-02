//package com.example.SpringBoard.service;
//
//import com.example.SpringBoard.entity.User;
//import com.example.SpringBoard.exceptions.DataNotFoundException;
//import com.example.SpringBoard.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@RequiredArgsConstructor
//@Service
//public class UserService{
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    public User create(String username, String email, String password){
//        User user = new User();
//        user.setUsername(username);
//        user.setEmail(email);
//        user.setPassword(passwordEncoder.encode(password));
//        this.userRepository.save(user);
//        return user;
//    }
//
//    public User getUser(String username) {
//        Optional<User> siteUser = this.userRepository.findByusername(username);
//        if (siteUser.isPresent()) {
//            return siteUser.get();
//        } else {
//            throw new DataNotFoundException("user not found");
//        }
//    }
//}
