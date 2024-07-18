package com.example.SpringBoard.lv1.entity;

import com.example.SpringBoard.lv1.dto.PostCreateRequestDTO;
import com.example.SpringBoard.lv1.dto.PostUpdateRequestDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/*
이노베이션 캠프 LV-1 : 게시물 Entity
 */

@Getter
@Entity
@NoArgsConstructor
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

    public Post(PostCreateRequestDTO postCreateRequestDTO){
        this.title = postCreateRequestDTO.getTitle();
        this.writer = postCreateRequestDTO.getWriter();
        this.password = postCreateRequestDTO.getPassword();
        this.text = postCreateRequestDTO.getText();
    }

    public void update(PostUpdateRequestDTO postUpdateRequestDTO){
        this.title = postUpdateRequestDTO.getTitle();
        this.writer = postUpdateRequestDTO.getWriter();
        this.text = postUpdateRequestDTO.getText();
    }
}
