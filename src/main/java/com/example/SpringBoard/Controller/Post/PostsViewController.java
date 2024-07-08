package com.example.SpringBoard.Controller.Post;

import com.example.SpringBoard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/*
이노베이션 캠프 LV-1 : 익명 게시판
 */

@RequiredArgsConstructor
@Controller
@RequestMapping("/posts")
public class PostsViewController {
    private final PostService postService;

    /*게시물 목록 페이지 */
    @GetMapping("")
    public String boards(Model model,@RequestParam(value="page", defaultValue="0") int page) {
        model.addAttribute("menu","posts");
        model.addAttribute("page",page);
        return "posts/posts";
    }

    /*게시물 수정 페이지 */
    @GetMapping(value = "/write")
    public String write(Model model) {
        model.addAttribute("menu","posts");
        return "posts/write";
    }

    /*게시물 상세보기 */
    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("menu","posts");
        return "posts/detail";
    }

    /*게시물 수정 페이지 */
    @GetMapping(value = "/edit/{id}")
    public String edit(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("menu","posts");
        model.addAttribute("id",id);
        return "posts/edit";
    }
}

