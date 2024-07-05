package com.example.SpringBoard.DTO.backoffice;
import com.example.SpringBoard.Enum.backoffice.Role;
import lombok.Getter;

@Getter
public class SignupRequestDTO {
    private String email;
    private String password;
    private String department;
    private Role role;
}
