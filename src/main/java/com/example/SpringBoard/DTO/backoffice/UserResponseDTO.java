package com.example.SpringBoard.DTO.backoffice;

import com.example.SpringBoard.Enum.backoffice.Role;
import com.example.SpringBoard.entity.backoffice.User;
import jakarta.persistence.*;

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
