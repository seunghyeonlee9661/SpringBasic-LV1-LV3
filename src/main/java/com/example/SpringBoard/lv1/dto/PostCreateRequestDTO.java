package com.example.SpringBoard.lv1.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PostCreateRequestDTO {
    
    @NotBlank(message = "제목을 입력해주세요")
    private String title;
    @NotBlank(message = "작성자를 입력해주세요")
    private String writer;
    @NotBlank(message = "암호를 입력해주세요")
    private String password;
    @NotBlank(message = "내용을 입력해주세요")
    private String text;
}
