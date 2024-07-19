package com.example.SpringBoard.lv3.controller;


import com.example.SpringBoard.lv3.dto.*;
import com.example.SpringBoard.lv3.service.BackofficeService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class BackofficeRestController {

    private final BackofficeService backofficeService;

    /* 사용자 추가 */
    @PostMapping("/user")
    public ResponseEntity<String> createUser(@RequestBody @Valid SignupRequestDTO signupRequestDTO) {
        return backofficeService.createUser(signupRequestDTO);
    }

    /* 로그인 */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO, HttpServletResponse response) {
        return backofficeService.login(loginRequestDTO,response);
    }

    /*____________________강의__________________________*/

    /* 강의 목록 불러오기 */
    @GetMapping("/lecture")
    public Page<LectureResponseDTO> findLectures(@RequestParam(value="page", defaultValue="0") int page, @RequestParam(value="category", defaultValue="") String category) {
        return backofficeService.findLectures(page, category);
    }

    /* 강의 내용 불러오기 */
    @GetMapping("/lecture/{id}")
    public ResponseEntity<LectureResponseDTO> findLecture(@PathVariable("id") int id) {
        return backofficeService.findLecture(id);
    }

    /* 강의 추가 */
    @PostMapping("/lecture")
    public ResponseEntity<String> createLecture(@RequestBody @Valid LectureRequestDTO lectureRequestDTO) {
        return backofficeService.createLecture(lectureRequestDTO);
    }

    /* 강의 삭제 */
    @DeleteMapping("/lecture")
    public ResponseEntity<String> removeLecture(@RequestParam("id") int id) {
        return backofficeService.removeLecture(id);
    }

    /* 강의 수정 */
    @PutMapping("/lecture")
    public ResponseEntity<String> updateLecture(@RequestBody @Valid LectureUpdateRequestDTO lectureUpdateRequestDTO) {
        return backofficeService.updateLecture(lectureUpdateRequestDTO);
    }
    /*____________________강사__________________________*/
    /* 강사 목록 불러오기 */
    @GetMapping("/teacher")
    public ResponseEntity<List<TeacherResponseDTO>> findTeachers() {
        return backofficeService.findTeachers();
    }

    /* 강사 정보 불러오기 */
    @GetMapping("/teacher/{id}")
    public ResponseEntity<TeacherResponseDTO> findTeacher(@PathVariable("id") int id) {
        return backofficeService.findTeacher(id);
    }

    /* 강사 추가 */
    @PostMapping("/teacher")
    public ResponseEntity<String> createTeacher(@RequestBody @Valid TeacherCreateRequestDTO teacherCreateRequestDTO) {
        return backofficeService.createLecture(teacherCreateRequestDTO);
    }

    /* 강사 삭제 */
    @DeleteMapping("/teacher")
    public ResponseEntity<String> removeTeacher(@RequestParam("id") int id) {
        return backofficeService.removeTeacher(id);
    }

    /* 강사 수정 */
    @PutMapping("/teacher")
    public ResponseEntity<String> updateTeacher(@RequestBody @Valid TeacherUpdateRequestDTO teacherUpdateRequestDTO) {
        return backofficeService.updateTeacher(teacherUpdateRequestDTO);
    }

}
