package com.example.SpringBoard.entity.posts;

import com.example.SpringBoard.DTO.posts.PostRequestDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/*
이노베이션 캠프 LV-1 : 게시물 Entity
 */

@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",nullable = false, columnDefinition = "int")
    private int id;

    @Column(name="title",nullable = false, columnDefinition = "varchar(100)")
    private String title;

    @Column(name="writer",nullable = false, columnDefinition = "varchar(50)")
    private String writer;

    @Column(name="password",nullable = false, columnDefinition = "varchar(255)")
    private String password;

    @Column(name="text",columnDefinition = "TEXT")
    private String text;

    @CreationTimestamp
    @Column(name="date")
    private LocalDateTime date;

    public Post() {
    }

    public Post(PostRequestDTO postRequestDTO){
        this.title = postRequestDTO.getTitle();
        this.writer = postRequestDTO.getWriter();
        this.password = postRequestDTO.getPassword();
        this.text = postRequestDTO.getText();
    }
}
