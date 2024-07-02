package com.example.SpringBoard.DTO;

import com.example.SpringBoard.entity.Book;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BookResponseDTO {
    private int id;
    private String title;
    private String author;
    private String language;
    private String publisher;
    private LocalDateTime registDate;

    public BookResponseDTO(Book book){
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.language = book.getLanguage();
        this.publisher = book.getPublisher();
        this.registDate = book.getRegistDate();
    }

    public BookResponseDTO(int id, String title, String author, String language, String publisher, LocalDateTime registDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.language = language;
        this.publisher = publisher;
        this.registDate = registDate;
    }
}
