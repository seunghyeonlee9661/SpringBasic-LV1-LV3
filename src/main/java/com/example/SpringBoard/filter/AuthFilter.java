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

@Slf4j(topic = "AuthFilter")
@Component
@Order(2)
public class AuthFilter implements Filter {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public AuthFilter(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String url = httpServletRequest.getRequestURI();

        String[] paths = {"/posts","/books","/js","/css"};

        /* 경로에 포함되면 권한 확인 */
        if (StringUtils.hasText(url) && (Arrays.stream(paths).anyMatch(url::startsWith) || url.equals("/backoffice") || url.equals("/backoffice/login") || url.equals("/backoffice/signup") || url.equals("/")) ) {
            chain.doFilter(request, response);
        }
        else {
            try {
                String tokenValue = jwtUtil.getTokenFromRequest(httpServletRequest);
                if (StringUtils.hasText(tokenValue)) { // 토큰이 존재하면 검증 시작
                    // JWT 토큰 substring
                    String token = jwtUtil.substringToken(tokenValue);
                    // 토큰 검증
                    if (!jwtUtil.validateToken(token)) {
                        throw new IllegalArgumentException("Token Error");
                    }
                    // 토큰에서 사용자 정보 가져오기
                    Claims info = jwtUtil.getUserInfoFromToken(token);
                    User backofficeUser = userRepository.findByEmail(info.getSubject()).orElseThrow(() ->
                            new NullPointerException("Not Found User")
                    );
                    request.setAttribute("user", backofficeUser);
                    chain.doFilter(request, response); // 다음 Filter 로 이동
                } else {
                    throw new IllegalArgumentException("Not Found Token");
                }
            } catch (Exception e) {
                // 예외 발생 시 로그인 페이지로 리다이렉트
                httpServletResponse.sendRedirect("/backoffice");
            }
        }

    }

}