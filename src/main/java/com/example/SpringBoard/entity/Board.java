package com.example.SpringBoard.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;

    @Column(columnDefinition = "text")
    private String text;

    @UpdateTimestamp
    private LocalDateTime date;


    public Board() {
    }

    public Board(String title, String text) {
        this.title = title;
        this.text = text;
    }

}
