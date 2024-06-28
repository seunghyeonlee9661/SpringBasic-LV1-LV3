package com.example.SpringBoard.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="post_id",nullable = false, columnDefinition = "int")
    private int id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name="user_name",referencedColumnName = "user_name")
    private User user;


    @Column(name="post_title",nullable = false, columnDefinition = "varchar(100)")
    private String title;

    @Column(name="post_text",columnDefinition = "TEXT")
    private String text;

    @CreationTimestamp
    @Column(name="post_date")
    private LocalDateTime date;

    public Post() {
    }
}
