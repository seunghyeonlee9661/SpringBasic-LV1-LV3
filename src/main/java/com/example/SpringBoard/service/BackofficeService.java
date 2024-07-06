package com.example.SpringBoard.service;

import com.example.SpringBoard.DTO.backoffice.LectureRequestDTO;
import com.example.SpringBoard.DTO.backoffice.LoginRequestDTO;
import com.example.SpringBoard.DTO.backoffice.SignupRequestDTO;
import com.example.SpringBoard.DTO.backoffice.TeacherRequestDTO;
import com.example.SpringBoard.config.JwtUtil;
import com.example.SpringBoard.entity.backoffice.Lecture;
import com.example.SpringBoard.entity.backoffice.Teacher;
import com.example.SpringBoard.entity.backoffice.User;
import com.example.SpringBoard.entity.books.Book;
import com.example.SpringBoard.repository.backoffice.LectureRepository;
import com.example.SpringBoard.repository.backoffice.TeacherRepository;
import com.example.SpringBoard.repository.backoffice.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BackofficeService {

    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;
    private final LectureRepository lectureRepository;


    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public BackofficeService(UserRepository userRepository,TeacherRepository teacherRepository,LectureRepository lectureRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.teacherRepository = teacherRepository;
        this.lectureRepository = lectureRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    /* 강의 목록 불러오기 */
    public Page<Lecture> getLectures(int page, String category) {// DB 조회
        Pageable pageable = PageRequest.of(page, 10);
        return (category.isEmpty()) ? lectureRepository.findAll(pageable) : lectureRepository.findByCategory(category,pageable);
    }
    /* 페이지에 해당하는 도서 목록 불러오기 */
    public Lecture getLecture(int id) {// DB 조회
        return lectureRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No Lecture"));
    }

    /* 페이지에 해당하는 도서 목록 불러오기 */
    public List<Lecture> getLecturesByTeacherId(int id) {// DB 조회
        return lectureRepository.findByTeacherId(id);
    }

    /* 강사 목록 불러오기 */
    public List<Teacher> getTeachers() {// DB 조회
        return teacherRepository.findAll();
    }
    /* 페이지에 해당하는 도서 목록 불러오기 */
    public Teacher getTeacher(int id) {// DB 조회
        return teacherRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No Teacher"));
    }

    /* 사용자 추가 */
    public ResponseEntity<String> create(SignupRequestDTO signupRequestDTO){
        try {
            if (userRepository.findByEmail(signupRequestDTO.getEmail()).isPresent()) throw new IllegalArgumentException("중복된 Email 입니다.");
            User backofficeUser = new User(signupRequestDTO);
            backofficeUser.setPassword(passwordEncoder.encode(backofficeUser.getPassword()));
            userRepository.save(backofficeUser);
            return ResponseEntity.ok("");
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /* 강사 추가 */
    public ResponseEntity<String> create(TeacherRequestDTO teacherRequestDTO){
        try {
            teacherRepository.save(new Teacher(teacherRequestDTO));
            return ResponseEntity.ok("강사가 추가되었습니다.");
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /* 강의 추가 */
    public ResponseEntity<String> create(LectureRequestDTO lectureRequestDTO){
        try {
            Optional<Teacher> optionalTeacher = teacherRepository.findById(lectureRequestDTO.getTeacher_id());
            if(optionalTeacher.isPresent()){
                Teacher teacher = optionalTeacher.get();
                System.out.println(teacher);
                Lecture lecture = new Lecture(lectureRequestDTO);
                lecture.setTeacher(teacher);
                System.out.println(lecture);
                lectureRepository.save(lecture);
                return ResponseEntity.ok("강의가 추가되었습니다.");
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("강사가 존재하지 않습니다.");
            }
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /* 로그인 */
    public ResponseEntity<String> login(LoginRequestDTO loginRequestDTO, HttpServletResponse res) {
        try {
            User backofficeUser = userRepository.findByEmail(loginRequestDTO.getEmail()).orElseThrow(() -> new IllegalArgumentException("등록된 사용자가 없습니다."));
            if (!passwordEncoder.matches(loginRequestDTO.getPassword(), backofficeUser.getPassword())) {
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
            }
            String token = jwtUtil.createToken(backofficeUser.getEmail(), backofficeUser.getRole());
            jwtUtil.addJwtToCookie(token, res);
            return ResponseEntity.ok().header("Content-Type", "application/json").body("{\"message\":\"로그인 성공\"}");
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Content-Type", "application/json").body("{\"error\": \"" + e.getMessage() + "\"}");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("Content-Type", "application/json").body("{\"error\": \"내부 서버 오류\"}");
        }
    }

    /* 강사 삭제 */
    public ResponseEntity<String> deleteTeacher(int id){
        try {
            Optional<Teacher> optionalTeacher = teacherRepository.findById(id);
            if(optionalTeacher.isPresent()){
                teacherRepository.delete(optionalTeacher.get());
                return ResponseEntity.ok("강사가 삭제되었습니다.");
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("강사가 존재하지 않습니다.");
            }
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /* 강의 삭제 */
    public ResponseEntity<String> deleteLecture(int id){
        try {
            Optional<Lecture> optionalLecture = lectureRepository.findById(id);
            if(optionalLecture.isPresent()){
                lectureRepository.delete(optionalLecture.get());
                return ResponseEntity.ok("강의가 삭제되었습니다.");
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("강의가 존재하지 않습니다.");
            }
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
