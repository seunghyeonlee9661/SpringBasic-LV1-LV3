package com.example.SpringBoard.repository.posts;

import com.example.SpringBoard.entity.posts.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/*
이노베이션 캠프 LV-1 : 게시물 Repository
 */

public interface PostRepository extends JpaRepository<Post, Integer> {
    Page<Post> findAll(Pageable pageable);
}
