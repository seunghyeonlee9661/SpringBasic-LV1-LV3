package com.example.SpringBoard.Controller;

import com.example.SpringBoard.dto.User;
import com.example.SpringBoard.dto.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/login")
    public String login(String userId, String userPw, HttpSession session, Model model) {
        User user = userRepository.findById(userId);
        if (user != null && user.getPassword().equals(userPw)) {
            session.setAttribute("user", user);
            return "redirect:/boards";
        } else {
            model.addAttribute("error", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return "loginform";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/";
    }

    @GetMapping("/signup")
    public String signup(String userId, String userPw, HttpSession session, Model model) {
        User user = userRepository.findById(userId);
        if (user != null && user.getPassword().equals(userPw)) {
            session.setAttribute("user", user);
            return "redirect:/boards";
        } else {
            model.addAttribute("error", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return "loginform";
        }
    }
}
