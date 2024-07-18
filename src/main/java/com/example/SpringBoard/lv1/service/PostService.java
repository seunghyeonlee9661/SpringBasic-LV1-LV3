package com.example.SpringBoard.lv1.service;

import com.example.SpringBoard.lv1.dto.PostDeleteRequestDTO;
import com.example.SpringBoard.lv1.dto.PostCreateRequestDTO;
import com.example.SpringBoard.lv1.dto.PostResponseDTO;
import com.example.SpringBoard.lv1.dto.PostUpdateRequestDTO;
import com.example.SpringBoard.lv1.entity.Post;
import com.example.SpringBoard.lv1.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/*
이노베이션 캠프 LV-1 : 게시물 Service
*/
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    /* 게시물 생성 */
    public ResponseEntity<String> create(PostCreateRequestDTO postCreateRequestDTO) {
        Post post = postRepository.save(new Post(postCreateRequestDTO));
        return ResponseEntity.ok(String.valueOf(post.getId()));
    }

    /* 게시물 상세 내용 */
    public ResponseEntity<PostResponseDTO> getPost(Integer id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));
        return ResponseEntity.ok(new PostResponseDTO(post));
    }

    /* 게시물 목록 페이지 */
    public ResponseEntity<Page<PostResponseDTO>> getPostList(int page){
        return ResponseEntity.ok(this.postRepository.findAll(PageRequest.of(page, 10)).map(PostResponseDTO::new));
    }

    /* 게시물 삭제 */
    public ResponseEntity<String> delete(PostDeleteRequestDTO postDeleteRequestDTO) {
        return postRepository.findById(postDeleteRequestDTO.getId())
                .map(post -> {
                    if (post.getPassword().equals(postDeleteRequestDTO.getPassword())) {
                        postRepository.delete(post);
                        return ResponseEntity.ok("게시물이 삭제되었습니다.");
                    } else {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("암호가 올바르지 않습니다.");
                    }
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("게시물이 존재하지 않습니다."));
    }

    /* 게시물 수정 */
    public ResponseEntity<String> edit(PostUpdateRequestDTO postUpdateRequestDTO) {
        return postRepository.findById(postUpdateRequestDTO.getId())
                .map(post -> {
                    if (post.getPassword().equals(postUpdateRequestDTO.getPassword())) {
                        post.update(postUpdateRequestDTO);
                        postRepository.save(post);
                        return ResponseEntity.ok("게시물이 수정되었습니다.");
                    } else {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("암호가 올바르지 않습니다.");
                    }
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("게시물이 존재하지 않습니다."));
    }
}
