package com.example.SpringBoard.dto.backoffice;

import com.example.SpringBoard.Enum.backoffice.Role;
import com.example.SpringBoard.entity.backoffice.User;

public class UserResponseDTO {
    private String email;
    private String department;
    private Role role;

    public UserResponseDTO(User user) {
        this.email = user.getEmail();
        this.department = user.getDepartment();
        this.role = user.getRole();
    }
}
