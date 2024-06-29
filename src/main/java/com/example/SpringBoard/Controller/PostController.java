package com.example.SpringBoard.Controller;

import com.example.SpringBoard.entity.Post;
import com.example.SpringBoard.entity.User;
import com.example.SpringBoard.form.PostWriteForm;
import com.example.SpringBoard.service.PostService;
import com.example.SpringBoard.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final UserService userService;

    @GetMapping("")
    public String boards(Model model,@RequestParam(value="page", defaultValue="0") int page,@RequestParam(value="sort", defaultValue="0") int sort) {
        model.addAttribute("menu","posts");
        Page<Post> paging = this.postService.getPage(page,sort);
        model.addAttribute("paging", paging);
        model.addAttribute("sort", sort);
        return "posts";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/write")
    public String write(Model model, PostWriteForm postWriteForm) {
        model.addAttribute("menu","posts");
        return "posts/write";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/write")
    public String write(@Valid PostWriteForm postWriteForm, BindingResult bindingResult, Principal principal) {
        User user = this.userService.getUser(principal.getName());
        if (bindingResult.hasErrors()) {
            return "posts/write";
        }
        postService.create(postWriteForm.getTitle(),postWriteForm.getText(),user);
        return "redirect:/posts";
    }

    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Post post = this.postService.getPost(id);
        model.addAttribute("post", post);
        model.addAttribute("menu","posts");
        return "posts/detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/edit/{id}")
    public String edit(Model model, @PathVariable("id") Integer id, PostWriteForm postWriteForm,Principal principal) {
        Post post = this.postService.getPost(id);
        if(!post.getUser().getUsername().equals(principal.getName()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        postWriteForm.setText(post.getText());
        postWriteForm.setTitle(post.getTitle());
        model.addAttribute("menu","posts");
        return "posts/write";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/edit/{id}")
    public String edit(@Valid PostWriteForm postWriteForm, BindingResult bindingResult, Principal principal,@PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "posts/write";
        }
        Post post = this.postService.getPost(id);
        if(!post.getUser().getUsername().equals(principal.getName()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        this.postService.edit(post, postWriteForm.getTitle(),postWriteForm.getText());
        return String.format("redirect:/posts/detail/%s",id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String questionDelete(Principal principal, @PathVariable("id") Integer id) {
        Post post = this.postService.getPost(id);
        if (!post.getUser().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.postService.delete(post);
        return "redirect:/posts";
    }
}

