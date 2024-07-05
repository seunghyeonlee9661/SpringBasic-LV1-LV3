package com.example.SpringBoard.Controller;

import com.example.SpringBoard.DTO.backoffice.LoginRequestDTO;
import com.example.SpringBoard.DTO.backoffice.SignupRequestDTO;
import com.example.SpringBoard.service.AdminService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/backoffice")
public class BackofficeController {

    private final AdminService adminService;

    /* 관리자 페이지 불러오기 */
    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("menu","backoffice");
        return "backoffice/index";
    }

    /* 관리자 추가 */
    @ResponseBody
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequestDTO signupRequestDTO) {
        return adminService.create(signupRequestDTO);
    }

    /* 관리자 추가 */
    @ResponseBody
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequestDTO, HttpServletResponse response) {
        return adminService.login(loginRequestDTO,response);
    }

    /* 관리자 페이지 불러오기 */
    @GetMapping("/main")
    public String board(Model model) {
        model.addAttribute("menu","backoffice");
        return "backoffice/main";
    }
}
