package com.example.SpringBoard.dto.posts;

import lombok.Getter;

@Getter
public class PostRequestDTO {
    private String title;
    private String writer;
    private String password;
    private String text;
}
