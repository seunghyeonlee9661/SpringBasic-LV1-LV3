package com.example.SpringBoard.Controller.Backoffice;


import com.example.SpringBoard.DTO.backoffice.LectureRequestDTO;
import com.example.SpringBoard.DTO.backoffice.LoginRequestDTO;
import com.example.SpringBoard.DTO.backoffice.SignupRequestDTO;
import com.example.SpringBoard.DTO.backoffice.TeacherRequestDTO;
import com.example.SpringBoard.service.BackofficeService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/backoffice/api")
public class BackofficeRestController {

    private final BackofficeService backofficeService;

    /* 사용자 추가 */
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequestDTO signupRequestDTO) {
        return backofficeService.create(signupRequestDTO);
    }

    /* 로그인 */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequestDTO, HttpServletResponse response) {
        return backofficeService.login(loginRequestDTO,response);
    }
    
    /* 강사 추가 */
    @PostMapping("/addTeacher")
    public ResponseEntity<String> create(@RequestBody TeacherRequestDTO teacherRequestDTO) {
        return backofficeService.create(teacherRequestDTO);
    }

    /* 강의 추가 */
    @PostMapping("/addLecture")
    public ResponseEntity<String> create(@RequestBody LectureRequestDTO lectureRequestDTO) {
        return backofficeService.create(lectureRequestDTO);
    }

    /* 강의 추가 */
    @PostMapping("/lecture/{id}")
    public ResponseEntity<String> lecture(@RequestBody LectureRequestDTO lectureRequestDTO,@PathVariable("id") Integer id) {
        return backofficeService.create(lectureRequestDTO);
    }


    /* 강의 추가 */
    @PostMapping("/teacher/{id}")
    public ResponseEntity<String> teacher(@RequestBody LectureRequestDTO lectureRequestDTO,@PathVariable("id") Integer id) {
        return backofficeService.create(lectureRequestDTO);
    }


}
