package com.example.SpringBoard.lv1.dto;

import lombok.Getter;

@Getter
public class PostUpdateRequestDTO {
    private int id;
    private String title;
    private String writer;
    private String password;
    private String text;
}
