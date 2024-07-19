package com.example.SpringBoard.lv3.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequestDTO {
    @NotBlank(message = "이메일을 입력하세요")
    private String email;
    @NotBlank(message = "패스워드를 입력하세요")
    private String password;
}
