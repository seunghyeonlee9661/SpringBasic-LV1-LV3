package com.example.SpringBoard.global.filter;

import com.example.SpringBoard.global.config.JwtUtil;
import com.example.SpringBoard.lv3.entity.User;
import com.example.SpringBoard.lv3.repository.UserRepository;
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
        String[] paths = {"/posts", "/api/post","/books", "/api/book","/api/member","/api/loan", "/js", "/css"};

        // 특정 경로에 대한 요청은 권한 확인 없이 통과
        if (StringUtils.hasText(url) && (Arrays.stream(paths).anyMatch(url::startsWith)
                || url.equals("/backoffice")  // LV3 : 로그인 페이지
                || url.equals("/api/login") // LV3 : 로그인 요청
                || url.equals("/api/user")  // LV3 : 회원가입 요청
                || url.equals("/"))){
            chain.doFilter(request, response);
        } else {
            String tokenValue = jwtUtil.getTokenFromRequest(httpServletRequest);
            if (StringUtils.hasText(tokenValue)) { // 토큰이 존재하면 검증 시작
                log.info("토큰 있음");
                // JWT 토큰에서 Bearer 제거
                String token = jwtUtil.substringToken(tokenValue);
                // 토큰 검증
                try {
                    if (!jwtUtil.validateToken(token, httpServletResponse)) {
                        // 알림 메시지를 설정하고 로그인 페이지로 리다이렉트
                        httpServletRequest.getSession().setAttribute("authError", "Invalid token.");
                        httpServletResponse.sendRedirect("/backoffice");
                        return;
                    }
                    // 토큰에서 사용자 정보 가져오기
                    Claims info = jwtUtil.getUserInfoFromToken(token);
                    User user = userRepository.findByEmail(info.getSubject()).orElseThrow(() -> new NullPointerException("Not Found User"));
                    request.setAttribute("user", user);
                    chain.doFilter(request, response); // 다음 필터로 이동
                } catch (Exception e) {
                    log.error("Authentication error: ", e);
                    // 알림 메시지를 설정하고 로그인 페이지로 리다이렉트
                    httpServletRequest.getSession().setAttribute("authError", "Authentication error.");
                    httpServletResponse.sendRedirect("/backoffice");
                }
            } else {
                log.info("토큰 없음");
                // 알림 메시지를 설정하고 로그인 페이지로 리다이렉트
                httpServletRequest.getSession().setAttribute("authError", "Token is missing.");
                httpServletResponse.sendRedirect("/backoffice");
            }
        }
    }
}