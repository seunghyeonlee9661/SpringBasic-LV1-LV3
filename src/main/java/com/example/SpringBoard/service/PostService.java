package com.example.SpringBoard.service;

import com.example.SpringBoard.dto.posts.PostRequestDTO;
import com.example.SpringBoard.dto.posts.PostResponseDTO;
import com.example.SpringBoard.entity.posts.Post;
import com.example.SpringBoard.repository.posts.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> create(PostRequestDTO postRequestDTO){
        try {
            Post post = postRepository.save(new Post(postRequestDTO));
            return ResponseEntity.ok(String.valueOf(post.getId()));
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /* 게시물 상세 내용 */
    public Post getPost(Integer id) {
        return postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));
    }

    /* 게시물 목록 페이지 */
    public Page<PostResponseDTO> getPosts(int page){
        Pageable pageable = PageRequest.of(page, 10);
        return this.postRepository.findAll(pageable).map(PostResponseDTO::new);
    }

    /* 게시물 삭제 */
    public ResponseEntity<String> delete(int id) {
        try {
            Optional<Post> optionalPost = postRepository.findById(id);
            if(optionalPost.isPresent()){
                postRepository.delete(optionalPost.get());
                return ResponseEntity.ok("게시물이 삭제되었습니다.");
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("게시물이 존재하지 않습니다.");
            }
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /* 게시물 수정 */
    public ResponseEntity<String> edit(int id, PostRequestDTO postRequestDTO){
        try {
            Optional<Post> optionalPost = postRepository.findById(id);
            if(optionalPost.isPresent()){
                Post post = optionalPost.get();
                if(post.getPassword().equals(postRequestDTO.getPassword())){
                    post.setTitle(postRequestDTO.getTitle());
                    post.setText(postRequestDTO.getText());
                    post.setWriter(postRequestDTO.getWriter());
                    postRepository.save(post);
                    return ResponseEntity.ok("게시물이 수정되었습니다.");
                }else{
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("암호가 올바르지 않습니다.");
                }
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("게시물이 존재하지 않습니다.");
            }
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /* 게시물 목록 페이지 */
    public ResponseEntity<String> checkPassword(int id,String password){
        try {
            Optional<Post> optionalPost = postRepository.findById(id);
            if(optionalPost.isPresent() && optionalPost.get().getPassword().equals(password)){
                return ResponseEntity.ok("비밀번호 확인 완료");
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("비밀번호가 올바르지 않습니다.");
            }
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
