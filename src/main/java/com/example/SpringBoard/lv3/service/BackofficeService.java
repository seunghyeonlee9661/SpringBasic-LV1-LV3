package com.example.SpringBoard.lv3.service;

import com.example.SpringBoard.global.config.JwtUtil;
import com.example.SpringBoard.lv3.dto.*;
import com.example.SpringBoard.lv3.entity.Lecture;
import com.example.SpringBoard.lv3.entity.Teacher;
import com.example.SpringBoard.lv3.entity.User;
import com.example.SpringBoard.lv3.entity.UserRole;
import com.example.SpringBoard.lv3.repository.LectureRepository;
import com.example.SpringBoard.lv3.repository.TeacherRepository;
import com.example.SpringBoard.lv3.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/*
    LV3 : 백오피스 Service
*/
@Service
@AllArgsConstructor
public class BackofficeService {

    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;
    private final LectureRepository lectureRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    /*----------------------회원정보--------------------------------*/

    /* 로그인 */
    public ResponseEntity<String> login(LoginRequestDTO loginRequestDTO, HttpServletResponse res) {
        User user = userRepository.findByEmail(loginRequestDTO.getEmail()).orElseThrow(() -> new IllegalArgumentException("등록된 사용자가 없습니다."));
        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호가 일치하지 않습니다.");
        }
        /* 사용자 이메일, 사용자 역할 기반으로 토큰 생성 */
        String token = jwtUtil.createToken(user.getEmail(), user.getUserRole());
        /* 쿠키에 토큰 저장 */
        jwtUtil.addJwtToCookie(token, res);
        return ResponseEntity.ok().body("로그인 성공");
    }

    /* 회원가입 */
    public ResponseEntity<String> createUser(SignupRequestDTO signupRequestDTO){
        if (userRepository.findByEmail(signupRequestDTO.getEmail()).isPresent()) throw new IllegalArgumentException("중복된 Email 입니다.");
        if(signupRequestDTO.getDepartment().equals("marketing") && UserRole.MANAGER == signupRequestDTO.getUserRole()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("마케팅 부서는 MANAGER 권한을 가질 수 없습니다.");
        }
        User user = new User(signupRequestDTO,passwordEncoder.encode(signupRequestDTO.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    /*------------------------강사----------------------------------*/

    /* 강사 목록 불러오기 */
    public ResponseEntity<List<TeacherResponseDTO>> findTeachers() {
        return ResponseEntity.ok(teacherRepository.findAll().stream().map(TeacherResponseDTO::new).collect(Collectors.toList()));
    }

    /* 강사 추가 */
    @Transactional
    public ResponseEntity<String> createLecture(TeacherCreateRequestDTO teacherCreateRequestDTO){
        teacherRepository.save(new Teacher(teacherCreateRequestDTO));
        return ResponseEntity.ok("강사가 추가되었습니다.");
    }

    /* 강사 정보 불러오기 */
    public ResponseEntity<TeacherResponseDTO> findTeacher(int id) {// DB 조회
        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No Teacher"));
        return ResponseEntity.ok(new TeacherResponseDTO(teacher));
    }

    /* 강사 삭제 */
    @Transactional
    public ResponseEntity<String> removeTeacher(int id){
        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No Teacher"));
        teacherRepository.delete(teacher);
        return ResponseEntity.ok("강사가 삭제되었습니다.");
    }

    /* 강사 수정 */
    @Transactional
    public ResponseEntity<String> updateTeacher(TeacherUpdateRequestDTO teacherUpdateRequestDTO) {
        Teacher teacher = teacherRepository.findById(teacherUpdateRequestDTO.getId()).orElseThrow(() -> new IllegalArgumentException("강사가 존재하지 않습니다."));
        teacher.update(teacherUpdateRequestDTO);
        return ResponseEntity.ok("강사가 수정되었습니다.");
    }

    /*------------------------강의----------------------------------*/

    /* 강의 목록 불러오기 */
    public Page<LectureResponseDTO> findLectures(int page, String category) {// DB 조회
        Pageable pageable = PageRequest.of(page, 10);
        Page<Lecture> lectures = (category.isEmpty()) ? lectureRepository.findAll(pageable) : lectureRepository.findByCategoryOrderByRegistDesc(category, pageable);
        return lectures.map(LectureResponseDTO::new);
    }

    /* 강의 추가 */
    @Transactional
    public ResponseEntity<String> createLecture(LectureRequestDTO lectureRequestDTO){
        Teacher teacher = teacherRepository.findById(lectureRequestDTO.getTeacher_id()).orElseThrow(() -> new IllegalArgumentException("강사가 존재하지 않습니다."));
        lectureRepository.save( new Lecture(lectureRequestDTO, teacher));
        return ResponseEntity.ok("강의가 추가되었습니다.");
    }
    
    /* 강의 정보 불러오기 */
    public ResponseEntity<LectureResponseDTO> findLecture(int id) {// DB 조회
        Lecture lecture = lectureRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No Lecture"));
        return ResponseEntity.ok(new LectureResponseDTO(lecture));
    }

    /* 강의 수정 */
    @Transactional
    public ResponseEntity<String> updateLecture(LectureUpdateRequestDTO lectureUpdateRequestDTO){
        Teacher teacher = teacherRepository.findById(lectureUpdateRequestDTO.getTeacher_id()).orElseThrow(() -> new IllegalArgumentException("강사가 존재하지 않습니다."));
        Lecture lecture = lectureRepository.findById(lectureUpdateRequestDTO.getId()).orElseThrow(() -> new IllegalArgumentException("강의가 존재하지 않습니다."));
        lecture.update(lectureUpdateRequestDTO, teacher);
        return ResponseEntity.ok("강의가 수정되었습니다.");
    }

    /* 강의 삭제 */
    @Transactional
    public ResponseEntity<String> removeLecture(int id){
        Lecture lecture = lectureRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("강의가 존재하지 않습니다."));
        lectureRepository.delete(lecture);
        return ResponseEntity.ok("강의가 삭제되었습니다.");
    }
}
