package com.example.SpringBoard.service;

import com.example.SpringBoard.DTO.backoffice.*;
import com.example.SpringBoard.config.JwtUtil;
import com.example.SpringBoard.entity.backoffice.Lecture;
import com.example.SpringBoard.entity.backoffice.Teacher;
import com.example.SpringBoard.entity.backoffice.User;
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

/*
    LV3 : 백오피스 Service
*/
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
    /*----------------------회원정보--------------------------------*/
    /* 로그인 */
    public ResponseEntity<String> login(LoginRequestDTO loginRequestDTO, HttpServletResponse res) {
        try {
            User backofficeUser = userRepository.findByEmail(loginRequestDTO.getEmail()).orElseThrow(() -> new IllegalArgumentException("등록된 사용자가 없습니다."));
            if (!passwordEncoder.matches(loginRequestDTO.getPassword(), backofficeUser.getPassword())) {
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
            }
            String token = jwtUtil.createToken(backofficeUser.getEmail(), backofficeUser.getRole());
            jwtUtil.addJwtToCookie(token, res);
            return ResponseEntity.ok().body("로그인 성공");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /* 회원가입 */
    public ResponseEntity<String> signup(SignupRequestDTO signupRequestDTO){
        try {
            if (userRepository.findByEmail(signupRequestDTO.getEmail()).isPresent()) throw new IllegalArgumentException("중복된 Email 입니다.");
            User backofficeUser = new User(signupRequestDTO);
            backofficeUser.setPassword(passwordEncoder.encode(backofficeUser.getPassword()));
            userRepository.save(backofficeUser);
            return ResponseEntity.ok("회원가입이 완료되었습니다.");
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /*------------------------강사----------------------------------*/

    /* 강사 목록 불러오기 */
    public List<Teacher> getTeachers() {
        return teacherRepository.findAll();
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

    /* 강사 정보 불러오기 */
    public Teacher getTeacher(int id) {// DB 조회
        return teacherRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No Teacher"));
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

    /* 강사 수정 */
    public ResponseEntity<String> edit(int id, TeacherRequestDTO teacherRequestDTO) {
        try {
            Optional<Teacher> optionalTeacher = teacherRepository.findById(id);
            if (optionalTeacher.isPresent()) {
                Teacher teacher = optionalTeacher.get();
                teacher.setName(teacherRequestDTO.getName());
                teacher.setCompany(teacherRequestDTO.getCompany());
                teacher.setIntroduction(teacherRequestDTO.getIntroduction());
                teacher.setYear(teacherRequestDTO.getYear());
                teacher.setPhone(teacherRequestDTO.getPhone());
                teacherRepository.save(teacher); // 업데이트된 강사 저장
                return ResponseEntity.ok("강사가 수정되었습니다.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("강사가 존재하지 않습니다.");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /*------------------------강의----------------------------------*/

    /* 강의 목록 불러오기 */
    public Page<LectureResponseDTO> getLectures(int page, String category) {// DB 조회
        Pageable pageable = PageRequest.of(page, 10);
        Page<Lecture> lectures = (category.isEmpty()) ? lectureRepository.findAll(pageable) : lectureRepository.findByCategoryOrderByRegistDesc(category, pageable);
        return lectures.map(LectureResponseDTO::new);
    }

    /* 강의 추가 */
    public ResponseEntity<String> create(LectureRequestDTO lectureRequestDTO){
        try {
            Optional<Teacher> optionalTeacher = teacherRepository.findById(lectureRequestDTO.getTeacher_id());
            if(optionalTeacher.isPresent()){
                Lecture lecture = new Lecture(lectureRequestDTO);
                lecture.setTeacher(optionalTeacher.get());
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
    
    /* 강의 정보 불러오기 */
    public Lecture getLecture(int id) {// DB 조회
        return lectureRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No Lecture"));
    }

    /* 강의 수정 */
    public ResponseEntity<String> edit(int id, LectureRequestDTO lectureRequestDTO){
        try {
            Optional<Lecture> optionalLecture = lectureRepository.findById(id);
            if (optionalLecture.isPresent()) {
                Lecture lecture = optionalLecture.get();
                lecture.setTitle(lectureRequestDTO.getTitle());
                lecture.setPrice(lectureRequestDTO.getPrice());
                lecture.setIntroduction(lectureRequestDTO.getIntroduction());
                lecture.setCategory(lectureRequestDTO.getCategory());
                if(lecture.getTeacher().getId() != lectureRequestDTO.getTeacher_id())
                    lecture.setTeacher(getTeacher(lectureRequestDTO.getTeacher_id()));
                lectureRepository.save(lecture); // 업데이트된 강의 저장
                return ResponseEntity.ok("강의가 수정되었습니다.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("강의가 존재하지 않습니다.");
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
