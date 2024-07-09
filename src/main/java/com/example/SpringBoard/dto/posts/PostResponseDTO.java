package com.example.SpringBoard.dto.posts;

import com.example.SpringBoard.entity.posts.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDTO {
    private int id;
    private String title;
    private String writer;
    private String password;
    private String text;
    private LocalDateTime date;

    public PostResponseDTO(Post post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.writer = post.getWriter();
        this.password = post.getPassword();
        this.text = post.getText();
        this.date = post.getDate();
    }
}
