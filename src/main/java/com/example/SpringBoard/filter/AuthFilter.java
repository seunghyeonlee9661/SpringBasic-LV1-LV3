package com.example.SpringBoard.filter;

import com.example.SpringBoard.config.JwtUtil;
import com.example.SpringBoard.entity.backoffice.User;
import com.example.SpringBoard.repository.backoffice.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import java.io.IOException;
import java.util.Arrays;

/**
 * JWT 토큰을 이용한 인증 필터
 */
@Slf4j(topic = "AuthFilter")
@Component
@Order(2)
public class AuthFilter implements Filter {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    // UserRepository와 JwtUtil을 주입 받는 생성자
    public AuthFilter(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String url = httpServletRequest.getRequestURI();

        // 인증이 필요 없는 경로 목록
        String[] paths = {"/posts", "/books", "/js", "/css"};

        // 특정 경로에 대한 요청은 권한 확인 없이 통과
        if (StringUtils.hasText(url) && (Arrays.stream(paths).anyMatch(url::startsWith)
                || url.equals("/backoffice")  // LV3 : 메인
                || url.equals("/backoffice/api/login") // LV3 : 로그인 요청
                || url.equals("/backoffice/api/signup")  // LV3 : 회원가입 요청
                || url.equals("/"))){
            chain.doFilter(request, response);
        } else {
            try {
                // 요청에서 JWT 토큰 추출
                String tokenValue = jwtUtil.getTokenFromRequest(httpServletRequest);
                if (StringUtils.hasText(tokenValue)) { // 토큰이 존재하면 검증 시작
                    // JWT 토큰에서 Bearer 제거
                    String token = jwtUtil.substringToken(tokenValue);
                    // 토큰 검증
                    if (!jwtUtil.validateToken(token, httpServletResponse)) {
                        throw new IllegalArgumentException("Token Error");
                    }
                    // 토큰에서 사용자 정보 가져오기
                    Claims info = jwtUtil.getUserInfoFromToken(token);
                    User user = userRepository.findByEmail(info.getSubject()).orElseThrow(() -> new NullPointerException("Not Found User"));
                    request.setAttribute("user", user);
                    chain.doFilter(request, response); // 다음 필터로 이동
                } else {
                    throw new IllegalArgumentException("Not Found Token");
                }
            } catch (IllegalArgumentException e) {
                // 토큰이 없거나 유효하지 않은 경우 로그인 페이지로 리다이렉트
                httpServletResponse.sendRedirect("/backoffice");
            } catch (Exception e) {
                // 기타 예외 발생 시 서버 오류로 응답
                httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                e.printStackTrace();
            }
        }
    }
}
