package com.example.SpringBoard.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/*
이노베이션 캠프 : 각 단계 접근을 위한 메인 페이지
 */

@RequiredArgsConstructor
@Controller
public class HomeController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("menu","home");
        return "index";
    }
}
