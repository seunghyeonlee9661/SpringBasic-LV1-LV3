package com.example.SpringBoard.lv2.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BookRequestDTO {
    private int id;
    private String title;
    private String author;
    private String language;
    private String publisher;
    private LocalDateTime registDate;
}
