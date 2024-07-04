package com.example.SpringBoard.DTO;
import com.example.SpringBoard.Enum.Role;
import lombok.Getter;

@Getter
public class SignupRequestDTO {
    private String email;
    private String password;
    private String department;
    private Role role;
}
