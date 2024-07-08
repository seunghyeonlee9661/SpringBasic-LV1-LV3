package com.example.SpringBoard.Controller.Post;

import com.example.SpringBoard.DTO.posts.PostRequestDTO;
import com.example.SpringBoard.DTO.posts.PostResponseDTO;
import com.example.SpringBoard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/*
이노베이션 캠프 LV-1 : 익명 게시판
 */

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts/api")
public class PostsRestController {
    private final PostService postService;

    /*게시물 작성 */
    @PostMapping("/post")
    public ResponseEntity<String> write(@RequestBody PostRequestDTO postRequestDTO) {
        return postService.create(postRequestDTO);
    }

    /*게시물 상세보기 */
    @GetMapping("/post")
    public PostResponseDTO post(@RequestParam("id") int id) {
        return new PostResponseDTO(postService.getPost(id));
    }


    /*게시물 상세보기 */
    @GetMapping("/posts")
    public Page<PostResponseDTO> posts(@RequestParam(value="page", defaultValue="0") int page) {
        return postService.getPosts(page);
    }

    /* 게시물 삭제 */
    @DeleteMapping("/post")
    public ResponseEntity<String> delete(@RequestParam("id") int id) {
        return postService.delete(id);
    }

    /*게시물 수정 페이지 */
    @PutMapping("/post")
    public  ResponseEntity<String> edit(@RequestBody PostRequestDTO postRequestDTO, @RequestParam("id") int id) {
        return postService.edit(id,postRequestDTO);
    }

    /*게시물 작성 */
    @PostMapping("/password")
    public ResponseEntity<String> write(@RequestBody Map<String,String> param) {
        return postService.checkPassword(Integer.parseInt(param.get("id")),param.get("input"));
    }

}

