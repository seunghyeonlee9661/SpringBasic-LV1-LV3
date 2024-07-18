package com.example.SpringBoard.lv3.dto;
import com.example.SpringBoard.lv3.entity.UserRole;
import lombok.Getter;

@Getter
public class SignupRequestDTO {
    private String email;
    private String password;
    private String department;
    private UserRole userRole;
}
