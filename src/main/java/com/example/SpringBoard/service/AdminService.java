package com.example.SpringBoard.service;
import com.example.SpringBoard.DTO.backoffice.LoginRequestDTO;
import com.example.SpringBoard.DTO.backoffice.SignupRequestDTO;
import com.example.SpringBoard.config.JwtUtil;
import com.example.SpringBoard.entity.backoffice.User;
import com.example.SpringBoard.repository.backoffice.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AdminService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    /* 관리자 추가 */
    public ResponseEntity<String> create(SignupRequestDTO signupRequestDTO){
        try {
            if (userRepository.findByEmail(signupRequestDTO.getEmail()).isPresent()) {
                throw new IllegalArgumentException("중복된 Email 입니다.");
            }
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

    public ResponseEntity<String> login(LoginRequestDTO loginRequestDTO, HttpServletResponse res) {
        try {
            // 사용자 확인
            User backofficeUser = userRepository.findByEmail(loginRequestDTO.getEmail()).orElseThrow(() -> new IllegalArgumentException("등록된 사용자가 없습니다."));
            // 비밀번호 확인
            if (!passwordEncoder.matches(loginRequestDTO.getPassword(), backofficeUser.getPassword())) {
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
            }
            // JWT 생성 및 쿠키에 저장 후 Response 객체에 추가
            String token = jwtUtil.createToken(backofficeUser.getEmail(), backofficeUser.getRole());
            jwtUtil.addJwtToCookie(token, res);
            return ResponseEntity.ok("로그인 성공");
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

}
