package com.example.SpringBoard.lv2.entity;

import com.example.SpringBoard.lv2.dto.BookRequestDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/*
이노베이션 캠프 LV-2 : 도서 Entity
 */

@Getter
@Entity
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false, columnDefinition = "int")
    private int id;

    @Column(name="title", nullable = false, columnDefinition = "varchar(255)")
    private String title;

    @Column(name="author", nullable = false, columnDefinition = "varchar(50)")
    private String author;

    @Column(name="language", nullable = false, columnDefinition = "varchar(255)")
    private String language;

    @Column(name="publisher", nullable = false, columnDefinition = "varchar(255)")
    private String publisher;

    @CreationTimestamp
    @Column(name="registDate")
    private LocalDateTime registDate;

    @OneToMany(mappedBy = "book", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Loan> loans = new ArrayList<>();

    public Book(BookRequestDTO requestDto) {
        this.title = requestDto.getTitle();
        this.author = requestDto.getAuthor();
        this.language = requestDto.getLanguage();
        this.publisher = requestDto.getPublisher();
    }
}
