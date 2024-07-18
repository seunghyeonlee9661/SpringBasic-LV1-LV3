package com.example.SpringBoard.lv1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PostDeleteRequestDTO {

    @NotNull
    private int id;

    @NotBlank(message = "패스워드를 입력해주세요")
    private String password;
}
