package com.example.SpringBoard.lv2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BookRequestDTO {
    @NotNull
    private int id;
    @NotBlank(message = "제목을 입력해주세요")
    private String title;
    @NotBlank(message = "작가를 입력해주세요")
    private String author;
    @NotBlank(message = "언어를 선택해주세요")
    private String language;
    @NotBlank(message = "출판사를 입력해주세요")
    private String publisher;
}
