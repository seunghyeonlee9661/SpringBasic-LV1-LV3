package com.example.SpringBoard.Controller.Backoffice;

import com.example.SpringBoard.DTO.backoffice.LectureRequestDTO;
import com.example.SpringBoard.DTO.backoffice.LoginRequestDTO;
import com.example.SpringBoard.DTO.backoffice.SignupRequestDTO;
import com.example.SpringBoard.DTO.backoffice.TeacherRequestDTO;
import com.example.SpringBoard.entity.backoffice.User;
import com.example.SpringBoard.service.BackofficeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/backoffice")
public class BackofficeViewController {

    private final BackofficeService backofficeService;

    /* 로그인 페이지 */
    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("menu","backoffice");
        return "backoffice/index";
    }


    /* 로그인 후 페이지 */
    @GetMapping("/main")
    public String board(Model model, HttpServletRequest req,@RequestParam(value="page", defaultValue="0") int page,@RequestParam(value="category", defaultValue="") String category) {
        model.addAttribute("menu","backoffice");
        model.addAttribute("user",(User) req.getAttribute("user"));
        model.addAttribute("paging",backofficeService.getLectures(page,category));
        model.addAttribute("category",category);
        model.addAttribute("teachers",backofficeService.getTeachers());
        return "backoffice/main";
    }
    /* 강의 페이지 */
    @GetMapping("/lecture/{id}")
    public String lecture(Model model,@PathVariable("id") int id) {
        model.addAttribute("menu","backoffice");
        model.addAttribute("lecture",backofficeService.getLecture(id));
        model.addAttribute("teachers",backofficeService.getTeachers());
        return "backoffice/lecture";
    }

    /* 강사 페이지 */
    @GetMapping("/teacher/{id}")
    public String teacher(Model model,@PathVariable("id") int id) {
        model.addAttribute("menu","backoffice");
        model.addAttribute("teacher",backofficeService.getTeacher(id));
        model.addAttribute("lectures",backofficeService.getLecturesByTeacherId(id));
        return "backoffice/teacher";
    }
}
