package com.example.SpringBoard.DTO.posts;

import lombok.Getter;

@Getter
public class PostRequestDTO {
    private String title;
    private String writer;
    private String password;
    private String text;
}
