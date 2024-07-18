package com.example.SpringBoard.lv3.dto;

import com.example.SpringBoard.lv3.entity.User;
import com.example.SpringBoard.lv3.entity.UserRole;

public class UserResponseDTO {
    private String email;
    private String department;
    private UserRole userRole;

    public UserResponseDTO(User user) {
        this.email = user.getEmail();
        this.department = user.getDepartment();
        this.userRole = user.getUserRole();
    }
}
