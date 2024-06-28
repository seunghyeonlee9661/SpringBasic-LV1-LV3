package com.example.SpringBoard.Controller;

import com.example.SpringBoard.entity.Post;
import com.example.SpringBoard.entity.User;
import com.example.SpringBoard.form.PostWriteForm;
import com.example.SpringBoard.service.PostService;
import com.example.SpringBoard.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.security.Principal;

@RequiredArgsConstructor
@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final UserService userService;

    @GetMapping("")
    public String boards(Model model) {
        model.addAttribute("menu","posts");
        model.addAttribute("posts",  this.postService.findAll());
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
        Post post = this.postService.getQuestion(id);
        model.addAttribute("post", post);
        model.addAttribute("menu","posts");
        return "posts/detail";
    }
}

