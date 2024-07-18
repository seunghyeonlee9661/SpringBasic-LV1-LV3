package com.example.SpringBoard.lv1.controller;

import com.example.SpringBoard.lv1.dto.PostCreateRequestDTO;
import com.example.SpringBoard.lv1.dto.PostDeleteRequestDTO;
import com.example.SpringBoard.lv1.dto.PostResponseDTO;
import com.example.SpringBoard.lv1.dto.PostUpdateRequestDTO;
import com.example.SpringBoard.lv1.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
이노베이션 캠프 LV-1 : 익명 게시판
 */

@RequiredArgsConstructor
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class PostsRestController {
    private final PostService postService;

    /*게시물 작성 */
    @PostMapping("/post")
    public ResponseEntity<String> createPost(@RequestBody @Valid PostCreateRequestDTO postCreateRequestDTO) {
        return postService.create(postCreateRequestDTO);
    }

    /* 게시물 상세보기 */
    @GetMapping("/post/{id}")
    public ResponseEntity<PostResponseDTO> findPost(@PathVariable("id") int id) {
        return postService.getPost(id);
    }

    /*게시물 목록보기 */
    @GetMapping("/post")
    public  ResponseEntity<Page<PostResponseDTO>> findPosts(@RequestParam(value="page", defaultValue="0") int page) {
        return postService.getPostList(page);
    }

    /* 게시물 삭제 */
    @DeleteMapping("/post")
    public ResponseEntity<String> removePost(@RequestBody @Valid PostDeleteRequestDTO postDeleteRequestDTO) {
        return postService.delete(postDeleteRequestDTO);
    }

    /*게시물 수정 페이지 */
    @PutMapping("/post")
    public  ResponseEntity<String> updatePost(@RequestBody @Valid PostUpdateRequestDTO postUpdateRequestDTO) {
        return postService.edit(postUpdateRequestDTO);
    }
}

