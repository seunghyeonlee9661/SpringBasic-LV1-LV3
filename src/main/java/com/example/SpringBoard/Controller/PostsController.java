package com.example.SpringBoard.Controller;

import com.example.SpringBoard.entity.posts.Post;
import com.example.SpringBoard.form.PostWriteForm;
import com.example.SpringBoard.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/*
이노베이션 캠프 LV-1 : 익명 게시판
 */

@RequiredArgsConstructor
@Controller
@RequestMapping("/posts")
public class PostsController {
    private final PostService postService;

    /*게시물 목록 페이지 */
    @GetMapping("")
    public String boards(Model model,@RequestParam(value="page", defaultValue="0") int page,@RequestParam(value="sort", defaultValue="0") int sort,@RequestParam(value = "kw", defaultValue = "") String kw,@RequestParam(value = "option", defaultValue = "title") String option) {
        model.addAttribute("menu","posts");
        Page<Post> paging = this.postService.getPage(page);
        model.addAttribute("paging", paging);
        model.addAttribute("sort", sort);
        model.addAttribute("kw", kw);
        model.addAttribute("option", option);
        return "posts/posts";
    }

    /*게시물 작성 페이지 */
    @GetMapping("/write")
    public String write(Model model, PostWriteForm postWriteForm) {
        model.addAttribute("menu","posts");
        return "posts/write";
    }

    /*게시물 작성 */
    @PostMapping("/write")
    public String write(@Valid PostWriteForm postWriteForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "posts/write";
        }
        Post post = postService.create(postWriteForm.getTitle(),postWriteForm.getWriter(),postWriteForm.getPassword(),postWriteForm.getText());
        return "redirect:/posts/detail/" + post.getId();
    }

    /*게시물 상세보기 */
    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Post post = this.postService.getPost(id);
        model.addAttribute("post", post);
        model.addAttribute("menu","posts");
        return "posts/detail";
    }

    /*게시물 수정 페이지 */
    @GetMapping(value = "/edit/{id}")
    public String edit(Model model, @PathVariable("id") Integer id, PostWriteForm postWriteForm) {
        Post post = this.postService.getPost(id);
        postWriteForm.setText(post.getText());
        postWriteForm.setTitle(post.getTitle());
        postWriteForm.setWriter(post.getWriter());
        postWriteForm.setPassword(post.getPassword());
        model.addAttribute("menu","posts");
        return "posts/write";
    }

    /*게시물 수정 */
    @PostMapping("/edit/{id}")
    public String edit(Model model,@Valid PostWriteForm postWriteForm, BindingResult bindingResult,@PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "posts/write";
        }
        Post post = this.postService.getPost(id);
        this.postService.edit(post, postWriteForm.getTitle(),postWriteForm.getText());
        model.addAttribute("menu","posts");
        return String.format("redirect:/posts/detail/%s",id);
    }

    /*게시물 삭제 */
    @GetMapping("/delete/{id}")
    public String questionDelete(@PathVariable("id") Integer id) {
        Post post = this.postService.getPost(id);
        this.postService.delete(post);
        return "redirect:/posts";
    }
}

