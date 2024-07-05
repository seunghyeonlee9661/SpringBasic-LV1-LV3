package com.example.SpringBoard.entity.books;

import com.example.SpringBoard.DTO.books.BookRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/*
이노베이션 캠프 LV-2 : 도서 Entity
 */

@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
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
    @Builder.Default
    private List<Loan> loans = new ArrayList<>();

    @Transient
    private boolean loanable;

    public Book() {
    }

    public Book(BookRequestDTO requestDto) {
        this.title = requestDto.getTitle();
        this.author = requestDto.getAuthor();
        this.language = requestDto.getLanguage();
        this.publisher = requestDto.getPublisher();
        this.registDate = requestDto.getRegistDate();
    }
}
