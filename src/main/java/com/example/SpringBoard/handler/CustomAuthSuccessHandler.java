//package com.example.SpringBoard.handler;
//
//import java.io.IOException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.DefaultRedirectStrategy;
//import org.springframework.security.web.RedirectStrategy;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
//import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
//import org.springframework.security.web.savedrequest.RequestCache;
//import org.springframework.stereotype.Component;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//@Component
//public class CustomAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
//
//    private final RequestCache requestCache = new HttpSessionRequestCache();
//    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,Authentication authentication) throws IOException {
//        String prevPage = (String) request.getSession().getAttribute("prevPage");
//
//        redirectStrategy.sendRedirect(request, response, prevPage);
//    }
//
//}