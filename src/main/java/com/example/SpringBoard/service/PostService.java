package com.example.SpringBoard.service;

import com.example.SpringBoard.entity.posts.Post;
import com.example.SpringBoard.exceptions.DataNotFoundException;
import com.example.SpringBoard.repository.posts.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
이노베이션 캠프 LV-1 : 게시물 Service
*/
@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final PasswordEncoder passwordEncoder;

    /* 게시물 생성 */
    public Post create(String title, String writer, String password, String text){
        Post post = new Post();
        post.setTitle(title);
        post.setWriter(writer);
        post.setPassword(password);
        post.setText(text);
        this.postRepository.save(post);
        return post;
    }

    /* 게시물 목록 페이지 */
    public Page<Post> getPage(int page){
        Pageable pageable = PageRequest.of(page, 10,Sort.by("date").descending());
        return this.postRepository.findAll(pageable);
    }

    /* 게시물 상세 내용 */
    public Post getPost(Integer id) {
        Optional<Post> post = this.postRepository.findById(id);
        if (post.isPresent()) {
            return post.get();
        } else {
            throw new DataNotFoundException("post not found");
        }
    }

    /* 게시물 삭제 */
    public void delete(Post post) {
        this.postRepository.delete(post);
    }

    /* 게시물 수정 */
    public void edit(Post post, String title, String text){
        post.setTitle(title);
        post.setText(text);
        this.postRepository.save(post);
    }
}
