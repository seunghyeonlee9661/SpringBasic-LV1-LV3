package com.example.SpringBoard.global.filter;

import com.example.SpringBoard.lv3.entity.User;
import com.example.SpringBoard.lv3.entity.UserRole;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

/* 권한 확인 필터 */
@Slf4j(topic = "RoleFilter")
@Component
@Order(3)
public class RoleFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String url = httpServletRequest.getRequestURI();
        String method = httpServletRequest.getMethod();


        // 특정 경로와 메서드에 대한 권한 확인
        if ((url.startsWith("/api/lecture") || url.startsWith("/api/teacher")) && ("PUT".equalsIgnoreCase(method) || "DELETE".equalsIgnoreCase(method))) {
            User user = (User) httpServletRequest.getAttribute("user");
            if (user == null || UserRole.MANAGER != user.getUserRole()) {
                log.info("권한에 따른 접근 금지 : " + user.getUserRole());
                httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                httpServletResponse.setContentType("application/json");
                httpServletResponse.setCharacterEncoding("UTF-8");
                String message = "권한이 올바르지 않습니다.";
                httpServletResponse.getWriter().write(message);
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
