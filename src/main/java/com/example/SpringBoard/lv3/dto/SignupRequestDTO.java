package com.example.SpringBoard.lv3.dto;
import com.example.SpringBoard.lv3.entity.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SignupRequestDTO {
    @NotBlank(message = "이메일을 입력하세요")
    private String email;
    @NotBlank(message = "패스워드를 입력하세요")
    private String password;
    @NotBlank(message = "부서를 입력하세요")
    private String department;
    @NotNull(message = "권한을 선택하세요")
    private UserRole userRole;
}
