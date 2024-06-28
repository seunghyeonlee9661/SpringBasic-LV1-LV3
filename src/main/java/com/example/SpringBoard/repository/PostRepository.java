package com.example.SpringBoard.repository;

import com.example.SpringBoard.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
    Post findByTitle(String Title);
    Post findByText(String text);
}
