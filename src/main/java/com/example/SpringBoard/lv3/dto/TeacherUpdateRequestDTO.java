package com.example.SpringBoard.lv3.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class TeacherUpdateRequestDTO {
    @NotNull
    private int id;
    @NotBlank(message = "이름을 입력하세요")
    private String name;
    @NotNull(message = "연차를 입력하세요")
    private int year;
    @NotBlank(message = "회사를 입력하세요")
    private String company;
    @NotBlank(message = "전화번호를 입력하세요")
    private String phone;
    @NotBlank(message = "소개말을 입력하세요")
    private String introduction;
}
