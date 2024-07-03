package com.example.SpringBoard.DTO;

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
