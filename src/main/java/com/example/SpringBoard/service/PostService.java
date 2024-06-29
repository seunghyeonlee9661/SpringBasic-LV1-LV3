package com.example.SpringBoard.service;

import com.example.SpringBoard.entity.Post;
import com.example.SpringBoard.entity.User;
import com.example.SpringBoard.exceptions.DataNotFoundException;
import com.example.SpringBoard.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

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

    public Page<Post> getPage(int page,int sort){
        Sort sorting = Sort.by("date").ascending();
        switch (sort){
            case 1:
                sorting =  Sort.by("title").ascending();
                break;
            case 2:
                sorting =  Sort.by("user").ascending();
                break;
        }
        Pageable pageable = PageRequest.of(page, 10,sorting);

        return this.postRepository.findAll(pageable);
    }

    public Post getPost(Integer id) {
        Optional<Post> post = this.postRepository.findById(id);
        if (post.isPresent()) {
            return post.get();
        } else {
            throw new DataNotFoundException("post not found");
        }
    }

    public void delete(Post post) {
        this.postRepository.delete(post);
    }

    public void edit(Post post, String title, String text){
        post.setTitle(title);
        post.setText(text);
        this.postRepository.save(post);
    }
}
