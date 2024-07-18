package com.example.SpringBoard.lv2.controller;

import com.example.SpringBoard.lv2.dto.*;
import com.example.SpringBoard.lv2.entity.Book;
import com.example.SpringBoard.lv2.entity.Loan;
import com.example.SpringBoard.lv2.entity.Member;
import com.example.SpringBoard.lv2.service.BooksService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
이노베이션 캠프 LV-2 : 도서 관리 서버
- 도서 등록 기능
- 도서 조회 기능 + (도서 대출 가능 여부 확인 )
- 도서 회원 등록 기능
- 도서 대출, 반납 기능 + ( 패널티 기능 )
- 대출 내역 조회 + (대출 내역 조회 조건 )
 */

@RequiredArgsConstructor
@Controller
@RequestMapping("/books")
public class BooksViewController {
    private final BooksService booksService;

    /* 도서 목록 페이지 */
    @GetMapping("")
    public String boards(Model model,@RequestParam(value="page", defaultValue="0") int page) {
        model.addAttribute("menu","books");
        model.addAttribute("page",page);
        return "books/books";
    }

    /* 도서 추가 페이지 */
    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("menu","books");
        return "books/add";
    }

    /* 회원 등록 페이지 */
    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("menu","books");
        return "books/signup";
    }

    /* 도서 상세보기 */
    @GetMapping("/detail/{id}")
    public String detail(Model model,@PathVariable("id") Integer id) {
        model.addAttribute("menu", "books");
        return "books/detail";
    }
}

