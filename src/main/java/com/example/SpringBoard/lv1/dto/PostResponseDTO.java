package com.example.SpringBoard.lv1.dto;

import com.example.SpringBoard.lv1.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDTO {
    private final int id;
    private final String title;
    private final String writer;
    private final String password;
    private final String text;
    private final LocalDateTime date;

    public PostResponseDTO(Post post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.writer = post.getWriter();
        this.password = post.getPassword();
        this.text = post.getText();
        this.date = post.getDate();
    }
}
