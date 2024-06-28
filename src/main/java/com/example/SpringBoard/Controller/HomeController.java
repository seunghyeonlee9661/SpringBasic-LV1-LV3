package com.example.SpringBoard.Controller;

import com.example.SpringBoard.entity.User;
import com.example.SpringBoard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class HomeController {

    private final UserService userService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("menu","home");
        return "index";
    }
}
