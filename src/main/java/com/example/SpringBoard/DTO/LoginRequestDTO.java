package com.example.SpringBoard.DTO;
import com.example.SpringBoard.Enum.Role;
import lombok.Getter;

@Getter
public class LoginRequestDTO {
    private String email;
    private String password;
}
