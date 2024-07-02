//package com.example.SpringBoard.Controller;
//import com.example.SpringBoard.form.UserCreateForm;
//import com.example.SpringBoard.service.UserService;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.dao.DataIntegrityViolationException;
//
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//
//import java.util.regex.Pattern;
//
//@RequiredArgsConstructor
//@Controller
//public class UserController {
//
//    private final UserService userService;
//
//    @GetMapping("/login")
//    public String login(Model model, HttpServletRequest request, Authentication authentication) {
//        HttpSession session = request.getSession();
//        if(authentication != null){ // 로그인 상태 아님
//            String referer = String.valueOf(session.getAttribute("Referer"));
//            if(referer == null)
//                return "redirect:/";
//            else {
//                session.removeAttribute("Referer");
//                return "redirect:"+ referer;
//            }
//        }else{ // 로그인 상태
//            if( session.getAttribute("Referer") == null){
//                String referer  = request.getHeader("Referer");
//                session.setAttribute("Referer", referer);
//            }
//            return "login";
//        }
//    }
//
//    @GetMapping("/signup")
//    public String signup(UserCreateForm userCreateForm,Authentication authentication) {
//        if(authentication != null){
//            return "redirect:/";
//        }
//        return "signup";
//    }
//
//    @PostMapping("/signup")
//    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult,HttpServletRequest request) {
//        if (bindingResult.hasErrors()) {
//            return "signup";
//        }
//        if (!Pattern.compile("^[a-zA-Z]{1}[a-zA-Z0-9_]{4,11}$").matcher(userCreateForm.getUsername()).find()) {
//            bindingResult.rejectValue("username", "usernameWrong", "아이디는 영문, 숫자, '_'로 구성된 5~12자로 구성되어야 합니다.");
//            return "signup";
//        }
//        if (!Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,20}$").matcher(userCreateForm.getPassword()).find()) {
//            bindingResult.rejectValue("password", "passwordWrong", "비밀번호는 영문+특수문자+숫자 8~20자로 구성되어야 합니다.");
//            return "signup";
//        }
//        if (!userCreateForm.getPassword().equals(userCreateForm.getPassword_check())) {
//            bindingResult.rejectValue("password_check", "passwordIncorrect", "2개의 패스워드가 일치하지 않습니다.");
//            return "signup";
//        }
//        try {
//            userService.create(userCreateForm.getUsername(),
//                    userCreateForm.getEmail(), userCreateForm.getPassword());
//        }catch(DataIntegrityViolationException e) {
//            e.printStackTrace();
//            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
//            return "signup";
//        }catch(Exception e) {
//            e.printStackTrace();
//            bindingResult.reject("signupFailed", e.getMessage());
//            return "signup";
//        }
//        return "redirect:" +  request.getHeader("Referer");
//    }
//}
