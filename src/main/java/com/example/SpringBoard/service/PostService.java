package com.example.SpringBoard.service;

import com.example.SpringBoard.entity.Post;
import com.example.SpringBoard.entity.User;
import com.example.SpringBoard.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    public Post create(String title, String text, User user){
        Post post = new Post();
        post.setTitle(title);
        post.setText(text);
        post.setUser(user);
        this.postRepository.save(post);
        return post;
    }

    public List<Post> findAll() {
        return this.postRepository.findAll();
    }
}
