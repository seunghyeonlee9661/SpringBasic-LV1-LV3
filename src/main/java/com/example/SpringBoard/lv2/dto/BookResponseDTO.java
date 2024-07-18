package com.example.SpringBoard.lv2.dto;

import com.example.SpringBoard.lv2.entity.Book;
import jakarta.persistence.Transient;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BookResponseDTO {
    private final int id;
    private final String title;
    private final String author;
    private final String language;
    private final String publisher;
    private final LocalDateTime registDate;
    private boolean loanable;

    public BookResponseDTO(Book book, boolean loanable){
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.language = book.getLanguage();
        this.publisher = book.getPublisher();
        this.registDate = book.getRegistDate();
        this.loanable = loanable;
    }
}
