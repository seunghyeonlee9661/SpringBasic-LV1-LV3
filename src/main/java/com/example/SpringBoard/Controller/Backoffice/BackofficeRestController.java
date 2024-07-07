package com.example.SpringBoard.Controller.Backoffice;


import com.example.SpringBoard.DTO.backoffice.*;
import com.example.SpringBoard.DTO.books.LoanResponseDTO;
import com.example.SpringBoard.entity.backoffice.Lecture;
import com.example.SpringBoard.entity.backoffice.Teacher;
import com.example.SpringBoard.entity.backoffice.User;
import com.example.SpringBoard.service.BackofficeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/backoffice/api")
public class BackofficeRestController {

    private final BackofficeService backofficeService;

    /* 사용자 추가 */
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequestDTO signupRequestDTO) {
        return backofficeService.signup(signupRequestDTO);
    }

    /* 로그인 */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequestDTO, HttpServletResponse response) {
        return backofficeService.login(loginRequestDTO,response);
    }

    /* 사용자 정보 불러오기 */
    @GetMapping("/user")
    public UserResponseDTO getUser(HttpServletRequest req) {
        return new UserResponseDTO((User) req.getAttribute("user"));
    }

    /*____________________강의__________________________*/

    /* 강의 목록 불러오기 */
    @GetMapping("/lectures")
    public Page<LectureResponseDTO>  getLectures(@RequestParam(value="page", defaultValue="0") int page,@RequestParam(value="category", defaultValue="") String category) {
        return backofficeService.getLectures(page, category);
    }

    /* 강의 내용 불러오기 */
    @GetMapping("/lecture/{id}")
    public LectureResponseDTO getLecture(@PathVariable("id") int id) {
        return new LectureResponseDTO(backofficeService.getLecture(id));
    }

    /* 강의 추가 */
    @PostMapping("/lecture")
    public ResponseEntity<String> addLecture(@RequestBody LectureRequestDTO lectureRequestDTO) {
        return backofficeService.create(lectureRequestDTO);
    }

    /* 강의 삭제 */
    @DeleteMapping("/lecture/{id}")
    public ResponseEntity<String> deleteLecture(@PathVariable("id") Integer id) {
        return backofficeService.deleteLecture(id);
    }

    /* 강의 수정 */
    @PutMapping("/lecture/{id}")
    public ResponseEntity<String> editLecture(@RequestBody LectureRequestDTO lectureRequestDTO,@PathVariable("id") Integer id) {
        return backofficeService.edit(id,lectureRequestDTO);
    }


    /*____________________강사__________________________*/

    /* 강사 목록 불러오기 */
    @GetMapping("/teachers")
    public List<TeacherResponseDTO> getTeachers() {
        return backofficeService.getTeachers().stream().map(TeacherResponseDTO::new).collect(Collectors.toList());
    }

    /* 강사 목록 불러오기 */
    @GetMapping("/teacher/{id}")
    public TeacherResponseDTO getTeacher(@PathVariable("id") int id) {
        return new TeacherResponseDTO(backofficeService.getTeacher(id));
    }

    /* 강사 추가 */
    @PostMapping("/teacher")
    public ResponseEntity<String> addTeacher(@RequestBody TeacherRequestDTO teacherRequestDTO) {
        return backofficeService.create(teacherRequestDTO);
    }

    /* 강사 삭제 */
    @DeleteMapping("/teacher/{id}")
    public ResponseEntity<String> deleteTeacher(@PathVariable("id") Integer id) {
        return backofficeService.deleteTeacher(id);
    }

    /* 강의 수정 */
    @PutMapping("/teacher/{id}")
    public ResponseEntity<String> editTeacher(@RequestBody TeacherRequestDTO teacherRequestDTO,@PathVariable("id") Integer id) {
        return backofficeService.edit(id,teacherRequestDTO);
    }

}
